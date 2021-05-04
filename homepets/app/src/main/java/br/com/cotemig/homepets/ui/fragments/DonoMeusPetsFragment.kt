package br.com.cotemig.homepets.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.FragmentDonoMeusPetsBinding
import br.com.cotemig.homepets.models.PetModel
import br.com.cotemig.homepets.models.PetsResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.ui.activities.DetalhesPetsActivity
import br.com.cotemig.homepets.ui.activities.DonoAddPetActivity
import br.com.cotemig.homepets.ui.activities.HomeActivity
import br.com.cotemig.homepets.ui.adapters.MeusPetsAdapter
import br.com.cotemig.homepets.util.RecyclerItemClickListener
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable


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
        var token = SharedPreferenceHelper.readString(activity,"userpreferences","token","")

         /* RETROFIT AQUI */
        RetrofitInitializer().serviceAPI().getPets(token= "Bearer $token").enqueue(object : Callback<List<PetsResponse>>{

            override fun onResponse(
                call: Call<List<PetsResponse>>?,
                response: Response<List<PetsResponse>>?
            ) {
                response?.let {
                    if(it.code() == 200){
                        binding.listapets.adapter = MeusPetsAdapter(activity,it.body())
                        binding.listapets.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
                        getRecycleItemClickListener(it.body())
                    }else{
                        MaterialDialog(activity).show {
                            title(R.string.erro)
                            message(null,it.errorBody()!!.string())
                            positiveButton(null,"Ok")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<PetsResponse>>?, t: Throwable?) {
                MaterialDialog(activity).show {
                    title(R.string.erro)
                    message(null,"API FORA DO AR")
                    positiveButton(null,"Ok")
                }
            }

        })
    }

    private fun goAddPetActivity(){
        var activity = context as HomeActivity
        startActivity(Intent(activity,DonoAddPetActivity::class.java))
    }

    private fun getRecycleItemClickListener(lista : List<PetsResponse>?) {
        binding.listapets.addOnItemTouchListener(RecyclerItemClickListener(context as HomeActivity,binding.listapets,object : RecyclerItemClickListener.OnItemClickListener{

            override fun onItemClick(view: View?, position: Int) {

                var petsResponse : PetsResponse = lista!![position]
                var intent = Intent(context as HomeActivity,DetalhesPetsActivity::class.java)
                intent.putExtra("objetoPets", petsResponse as Serializable)
                startActivity(intent)
            }

            override fun onLongItemClick(view: View?, position: Int) {
                TODO("Not yet implemented")
            }

        }))
    }

}