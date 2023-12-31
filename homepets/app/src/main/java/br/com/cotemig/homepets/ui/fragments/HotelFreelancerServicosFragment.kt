package br.com.cotemig.homepets.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.FragmentHotelFreelancerServicosBinding
import br.com.cotemig.homepets.models.ServicesResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.ui.activities.DetalhesServicosActivity
import br.com.cotemig.homepets.ui.activities.HomeActivity
import br.com.cotemig.homepets.ui.activities.HotelFreelancerAddServicoActivity
import br.com.cotemig.homepets.ui.adapters.MeusServicosAdapter
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class HotelFreelancerServicosFragment : Fragment(), MeusServicosAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHotelFreelancerServicosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelFreelancerServicosBinding.inflate(layoutInflater, container, false)
        freeColors()

        getServices()

        binding.addNovoServico.setOnClickListener {
            goAddNovoServico()
        }

        swipeList()

        return binding.root
    }

    private fun getServices(){
        var activity = context as HomeActivity
        var token = SharedPreferenceHelper.readString(activity,"userpreferences","token","")

        RetrofitInitializer().serviceAPI().getServices(token= "Bearer $token")
            .enqueue(object : Callback<List<ServicesResponse>> {

                override fun onResponse(
                    call: Call<List<ServicesResponse>>,
                    response: Response<List<ServicesResponse>>
                ){
                    response?.let {
                        if (it.code() == 200) {
                            binding.listaservicos.adapter = MeusServicosAdapter(activity, it.body(),this@HotelFreelancerServicosFragment)
                            binding.listaservicos.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        } else {
                            MaterialDialog(activity).show {
                                title(R.string.erro)
                                message(null,it.errorBody()!!.string())
                                positiveButton(null,"Ok")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ServicesResponse>>, t: Throwable) {
                    MaterialDialog(activity).show {
                        title(R.string.erro)
                        message(null,"API FORA DO AR")
                        positiveButton(null,"Ok")
                    }
                }

            })
    }

    private fun goAddNovoServico() {
        var activity = context as HomeActivity
        startActivity(Intent(activity, HotelFreelancerAddServicoActivity::class.java))
    }

    private fun freeColors() {
        var activity = context as HomeActivity
        var stats = SharedPreferenceHelper.readInt(activity, "userpreferences", "stats", -1)

        if (stats == 3) {
            binding.addNovoServico.imageTintList =
                ContextCompat.getColorStateList(activity, R.color.cyan)
        }
    }

    private fun swipeList(){
        binding.swipe.setOnRefreshListener {
            getServices()

            Executors.newSingleThreadScheduledExecutor().schedule({
                binding.swipe.isRefreshing = false
            },1,TimeUnit.SECONDS)
        }
    }

    override fun OnItemClick(position: Int, servico: ServicesResponse) {
        var intent =Intent(context as HomeActivity, DetalhesServicosActivity::class.java)
        intent.putExtra("objeto", servico as Serializable)
        startActivity(intent)

    }
}