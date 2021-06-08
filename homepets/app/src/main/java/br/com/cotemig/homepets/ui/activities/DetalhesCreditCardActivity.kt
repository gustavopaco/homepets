package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityDetalhesCreditCardBinding
import br.com.cotemig.homepets.models.CreditCardResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import com.maxpilotto.creditcardview.models.CardArea
import com.maxpilotto.creditcardview.models.CardInput
import com.maxpilotto.creditcardview.util.NumberFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalhesCreditCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesCreditCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesCreditCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCreditCard()

        binding.btnVoltar.setOnClickListener {
            finish()
        }

        binding.btnDeletar.setOnClickListener {
            deleteCreditCard()
        }
    }

    private fun getCreditCard() {
        var creditCardResponse = intent.getSerializableExtra("cartao") as CreditCardResponse

        binding.card.number = creditCardResponse.numero
        binding.card.holder = creditCardResponse.nomeTitular
        binding.card.expiry = creditCardResponse.validadeMesAno
        binding.card.cvv = creditCardResponse.codigoValidacao

        binding.card.numberFormat = NumberFormat("%d4 %d4 %d4 %d4",false)

        binding.card.setAreaClickListener { card, area ->
            if(area == CardArea.LEFT){
                binding.card.flipOnClick = true
            }else if(area == CardArea.RIGHT){
                binding.card.flipOnClick = true
            }
        }
    }

    private fun deleteCreditCard() {
        var token = SharedPreferenceHelper.readString(this,"userpreferences","token","")
        var creditCardResponse = intent.getSerializableExtra("cartao") as CreditCardResponse

        RetrofitInitializer().serviceAPI().deleteCreditCard(token= "Bearer $token",creditCardResponse.id).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                response?.let {
                    if (it.code() == 200){
                        MaterialDialog(this@DetalhesCreditCardActivity).show {
                            title(R.string.sucesso)
                            message(null, "Cartão de Crédito deletado com sucesso.")
                            positiveButton(R.string.ok){
                                finish() // MATANDO ACTIVITY DE CADASTRO APOS CRIAR CONTRATO E VOLTANDO PARA TELA LISTA DE CARTAO
                            }
                        }
                    }else{
                        MaterialDialog(this@DetalhesCreditCardActivity).show {
                            title(R.string.erro)
                            message(null,it.errorBody()!!.string())
                            positiveButton(null,"Ok")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                MaterialDialog(this@DetalhesCreditCardActivity).show {
                    title(R.string.erro)
                    message(null,"API FORA DO AR")
                    positiveButton(null,"Ok")
                }
            }
        })
    }
}