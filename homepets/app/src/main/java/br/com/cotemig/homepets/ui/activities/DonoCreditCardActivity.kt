package br.com.cotemig.homepets.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityDonoCreditCardBinding
import br.com.cotemig.homepets.models.CreditCardResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.ui.adapters.CreditCardAdapter
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class DonoCreditCardActivity : AppCompatActivity(), CreditCardAdapter.OnItemClickListener{

    private lateinit var binding: ActivityDonoCreditCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonoCreditCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCartoes()

        binding.addNovoCartao.setOnClickListener {
            goAddNovoCartao()
        }

        binding.btnVoltar.setOnClickListener {
            finish()
        }

        swipe()

    }

    private fun getCartoes() {
        var token = SharedPreferenceHelper.readString(this,"userpreferences","token","")

        RetrofitInitializer().serviceAPI().getCreditCards(token= "Bearer $token").enqueue(object : Callback<List<CreditCardResponse>>{
            override fun onResponse(
                call: Call<List<CreditCardResponse>>,
                response: Response<List<CreditCardResponse>>
            ) {
                response?.let {
                    if (it.code() == 200){
                        binding.listacartoes.adapter = CreditCardAdapter(this@DonoCreditCardActivity,it.body(),this@DonoCreditCardActivity)
                        binding.listacartoes.layoutManager = LinearLayoutManager(this@DonoCreditCardActivity,LinearLayoutManager.VERTICAL,false)
                    }else{
                        MaterialDialog(this@DonoCreditCardActivity).show {
                            title(R.string.erro)
                            message(null,it.errorBody()!!.string())
                            positiveButton(null,"Ok")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CreditCardResponse>>, t: Throwable) {
                MaterialDialog(this@DonoCreditCardActivity).show {
                    title(R.string.erro)
                    message(null, "API FORA DO AR")
                    positiveButton(null, "Ok")
                }
            }
        })
    }

    private fun swipe(){
        binding.swipe.setOnRefreshListener {
            getCartoes()
            Executors.newSingleThreadScheduledExecutor().schedule({
                binding.swipe.isRefreshing = false
            },1,TimeUnit.SECONDS)
        }

    }

    override fun OnItemClick(position: Int,creditCardResponse: CreditCardResponse) {
        var intent = Intent(this,DetalhesCreditCardActivity::class.java)
        intent.putExtra("cartao", creditCardResponse as Serializable)
        startActivity(intent)
    }

    private fun goAddNovoCartao() {
       startActivity(Intent(this,DonoAddCreditCardActivity::class.java))
    }

}