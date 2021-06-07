package br.com.cotemig.homepets.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityDonoHistoricoServicosBinding
import br.com.cotemig.homepets.models.DonoHFContractedServiceResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.ui.adapters.DonoContractedServiceAdapter
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class DonoHistoricoServicosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonoHistoricoServicosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonoHistoricoServicosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVoltar.setOnClickListener {
            finish()
        }
        getHistorico(this,binding.listaDonoHistoricoServicos)

        swipe(this, binding.listaDonoHistoricoServicos)
    }

    private fun swipe(activityEscolhida : Context, recyclerView: RecyclerView) {
        binding.swipe.setOnRefreshListener {
            getHistorico(activityEscolhida, recyclerView)
            Executors.newSingleThreadScheduledExecutor().schedule({
                binding.swipe.isRefreshing = false
            },1,TimeUnit.SECONDS)
        }
    }


    fun getHistorico(activityEscolhida: Context, recyclerView: RecyclerView){
        var token = SharedPreferenceHelper.readString(activityEscolhida,"userpreferences","token","")
        var stats = SharedPreferenceHelper.readInt(activityEscolhida,"userpreferences","stats",-1)

        RetrofitInitializer().serviceAPI().getDonoHFContractedService(token= "Bearer $token").enqueue(object : Callback<List<DonoHFContractedServiceResponse>>{
            override fun onResponse(call: Call<List<DonoHFContractedServiceResponse>>, responseHF: Response<List<DonoHFContractedServiceResponse>>) {
                responseHF?.let {
                    if(it.code() == 200){
                        recyclerView.adapter = DonoContractedServiceAdapter(activityEscolhida,it.body(),stats)
                        recyclerView.layoutManager = LinearLayoutManager(activityEscolhida,LinearLayoutManager.VERTICAL,false)
                    }else{
                        MaterialDialog(activityEscolhida).show {
                            title(R.string.erro)
                            message(null,it.errorBody()!!.string())
                            positiveButton(null,"Ok")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<DonoHFContractedServiceResponse>>, t: Throwable) {
                MaterialDialog(activityEscolhida).show {
                    title(R.string.erro)
                    message(null,"API FORA DO AR")
                    positiveButton(null,"Ok")
                }
            }

        })
    }
}