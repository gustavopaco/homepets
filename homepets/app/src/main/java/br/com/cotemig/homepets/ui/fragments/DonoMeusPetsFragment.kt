package br.com.cotemig.homepets.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cotemig.homepets.databinding.FragmentDonoMeusPetsBinding
import br.com.cotemig.homepets.models.PetModel
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.ui.activities.DonoAddPetActivity
import br.com.cotemig.homepets.ui.activities.HomeActivity
import br.com.cotemig.homepets.ui.adapters.MeusPetsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DonoMeusPetsFragment : Fragment() {

    private lateinit var binding: FragmentDonoMeusPetsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDonoMeusPetsBinding.inflate(layoutInflater,container,false)

        getMeusPets()

        binding.addNovoPet.setOnClickListener {
            goAddPetActivity()
        }

        return binding.root
    }

    fun getMeusPets(){

        var activity = context as HomeActivity

         /* RETROFIT AQUI */
        RetrofitInitializer().serviceAPI().getPets().enqueue(object : Callback<List<PetModel>>{

            override fun onResponse(
                call: Call<List<PetModel>>?,
                response: Response<List<PetModel>>?
            ) {
                response?.let {
                    if(it.code() == 200){
                        binding.listapets.adapter = MeusPetsAdapter(activity,it.body())
                        binding.listapets.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
                    }else{
                        Toast.makeText(activity,"API NAO FUNCIONANDO" + it.code(),
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<PetModel>>?, t: Throwable?) {
                Toast.makeText(activity,"API FORA DO AR",
                    Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun goAddPetActivity(){
        var activity = context as HomeActivity
        startActivity(Intent(activity,DonoAddPetActivity::class.java))
    }

}