package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.cotemig.homepets.databinding.ActivityDetalhesPetsBinding
import br.com.cotemig.homepets.models.PetsResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.Constantes
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
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

        var petsResponse = intent.extras!!.get("objetoPets") as PetsResponse

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

        var email = SharedPreferenceHelper.readString(this,"userpreferences","email","")
        var petsResponse = intent.extras!!.get("objetoPets") as PetsResponse

        RetrofitInitializer().serviceAPI().deletePet(email.toString(),petsResponse.id).enqueue(object : Callback<Void>{

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                response?.let {
                    if(it.code() == 204){
                        MaterialDialog.Builder(this@DetalhesPetsActivity).theme(Theme.LIGHT).title("Sucesso").content("Pet Deletado!").positiveText("Ok").show()
                        finish()
                    }else{
                        MaterialDialog.Builder(this@DetalhesPetsActivity).theme(Theme.LIGHT).title("Erro").content(it.errorBody()!!.string()).positiveText("Ok").show()
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                MaterialDialog.Builder(this@DetalhesPetsActivity).theme(Theme.LIGHT).title("Erro").content("API fora do AR").positiveText("Ok").show()
            }

        })

    }
}