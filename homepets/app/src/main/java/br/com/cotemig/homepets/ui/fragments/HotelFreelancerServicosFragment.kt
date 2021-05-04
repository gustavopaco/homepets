package br.com.cotemig.homepets.ui.fragments

import android.content.Intent
import android.content.res.Resources
import android.graphics.ColorFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.FragmentHotelFreelancerServicosBinding
import br.com.cotemig.homepets.models.ServiceModel
import br.com.cotemig.homepets.models.ServicesResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.ui.activities.DetalhesServicosActivity
import br.com.cotemig.homepets.ui.activities.HomeActivity
import br.com.cotemig.homepets.ui.activities.HotelFreelancerAddServicoActivity
import br.com.cotemig.homepets.ui.adapters.MeusServicosAdapter
import br.com.cotemig.homepets.util.RecyclerItemClickListener
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class HotelFreelancerServicosFragment : Fragment() {

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

        return binding.root
    }

    private fun getServices() {

        var activity = context as HomeActivity
        var email = SharedPreferenceHelper.readString(activity, "userpreferences", "email", "")
        var token = SharedPreferenceHelper.readString(activity,"userpreferences","token","")

        RetrofitInitializer().serviceAPI().getServices(token= "Bearer $token")
            .enqueue(object : Callback<List<ServicesResponse>> {

                override fun onResponse(
                    call: Call<List<ServicesResponse>>,
                    response: Response<List<ServicesResponse>>
                ) {
                    response?.let {
                        if (it.code() == 200) {
                            binding.listaservicos.adapter = MeusServicosAdapter(activity, it.body())
                            binding.listaservicos.layoutManager =
                                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                            getRecycleItemClickListener(it.body())
                        } else {
                            MaterialDialog.Builder(activity).title("Erro")
                                .content(it.errorBody()!!.string()).positiveText("Ok").show()
                        }
                    }
                }

                override fun onFailure(call: Call<List<ServicesResponse>>, t: Throwable) {
                    MaterialDialog.Builder(activity).title("Erro").content("API FORA DO AR")
                        .positiveText("Ok").show()
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

    private fun getRecycleItemClickListener(lista : List<ServicesResponse>?) {
        binding.listaservicos.addOnItemTouchListener(RecyclerItemClickListener(context as HomeActivity,binding.listaservicos,object : RecyclerItemClickListener.OnItemClickListener {

                    override fun onItemClick(view: View?, position: Int) {

                        var services : ServicesResponse = lista!![position]
                        var intent =Intent(context as HomeActivity, DetalhesServicosActivity::class.java)
                        intent.putExtra("objeto", services as Serializable)
                        startActivity(intent)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        TODO("Not yet implemented")
                    }

                })
        )

    }
}