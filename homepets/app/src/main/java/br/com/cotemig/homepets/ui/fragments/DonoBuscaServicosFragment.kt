package br.com.cotemig.homepets.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.FragmentDonoBuscaServicosBinding
import br.com.cotemig.homepets.models.ServiceSearchResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.ui.activities.DonoAddContractActivity
import br.com.cotemig.homepets.ui.activities.HomeActivity
import br.com.cotemig.homepets.ui.adapters.ServiceSearchAdapter
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class DonoBuscaServicosFragment : Fragment(), ServiceSearchAdapter.OnItemClickListener {

    private lateinit var binding: FragmentDonoBuscaServicosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDonoBuscaServicosBinding.inflate(layoutInflater, container, false)

        binding.btnPesquisarServicos.setOnClickListener {
            getSearch()
        }

        swipe()

        return binding.root
    }

    private fun getSearch() {
        var activity = context as HomeActivity
        var token = SharedPreferenceHelper.readString(activity, "userpreferences", "token", "")
        var pesquisa = binding.inputPesquisaServicos.text.toString()
        if (pesquisa.isNotEmpty()) {
            RetrofitInitializer().serviceAPI().getServiceSearch(token = "Bearer $token", pesquisa)
                .enqueue(object : Callback<List<ServiceSearchResponse>> {
                    override fun onResponse(
                        call: Call<List<ServiceSearchResponse>>,
                        response: Response<List<ServiceSearchResponse>>
                    ) {
                        response.let {
                            if (it.code() == 200) {

                                binding.listabuscaservicos.adapter =
                                    ServiceSearchAdapter(activity, it.body(), this@DonoBuscaServicosFragment)
                                binding.listabuscaservicos.layoutManager =
                                    LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                            } else {
                                MaterialDialog(activity).show {
                                    title(R.string.erro)
                                    message(null, "Pesquisa nao encontrada.")
                                    positiveButton(null, "Ok")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<ServiceSearchResponse>>, t: Throwable) {
                        MaterialDialog(activity).show {
                            title(R.string.erro)
                            message(null, "API FORA DO AR")
                            positiveButton(null, "Ok")
                        }
                    }

                })
        } else {
            binding.inputPesquisaServicos.error = "Informe o nome de algum serviço"
        }
    }

    private fun swipe() {
        binding.swipe.setOnRefreshListener {
            getSearch()
            Executors.newSingleThreadScheduledExecutor().schedule({
                binding.swipe.isRefreshing = false
            }, 1, TimeUnit.SECONDS)
        }
    }

    override fun OnItemClick(serviceSearchResponse: ServiceSearchResponse) {
        var intent = Intent(context as HomeActivity, DonoAddContractActivity::class.java)
        intent.putExtra("contrato", serviceSearchResponse as Serializable)
        startActivity(intent)
    }
}