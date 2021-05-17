package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityDetalhesPetsBinding
import br.com.cotemig.homepets.models.PetsResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.Constantes
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalhesPetsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetalhesPetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDetalhes()

        binding.btnVoltar.setOnClickListener {
            finish()
        }

        binding.btnDeletar.setOnClickListener {
            deletePet()
        }

    }

    private fun getDetalhes(){

      var petsResponse = intent.getSerializableExtra("objetoPets") as PetsResponse
        binding.inputNomePet.setText(petsResponse.nome)
        binding.inputRacaPet.setText(petsResponse.raca)


        when(petsResponse.sexo){
            Constantes.Feminino() -> binding.radiobtnFeminino.isChecked = true
            else -> binding.radiobtnMasculino.isChecked = true
        }

        when(petsResponse.tipoPet){
            1 -> binding.radiobtnCachorro.isChecked = true
            else -> binding.radiobtnGato.isChecked = true
        }

    }

    private fun deletePet(){

        var token = SharedPreferenceHelper.readString(this,"userpreferences","token","")
        var petsResponse = intent.getSerializableExtra("objetoPets") as PetsResponse

        RetrofitInitializer().serviceAPI().deletePet(token= "Bearer $token",petsResponse.id).enqueue(object : Callback<Void>{

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                response?.let {
                    if(it.code() == 200){
                        MaterialDialog(this@DetalhesPetsActivity).show {
                            title(R.string.sucesso)
                            message(R.string.petdeletado)
                            positiveButton(R.string.ok){
                                finish()
                            }
                        }
                    }else{
                        MaterialDialog(this@DetalhesPetsActivity).show {
                            title(R.string.erro)
                            message(null,it.errorBody()!!.string())
                            positiveButton(null,"Ok")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                MaterialDialog(this@DetalhesPetsActivity).show {
                    title(R.string.erro)
                    message(null,"API FORA DO AR")
                    positiveButton(null,"Ok")
                }
            }

        })

    }
}