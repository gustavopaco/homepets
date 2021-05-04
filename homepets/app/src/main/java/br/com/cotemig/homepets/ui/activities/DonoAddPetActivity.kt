package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import br.com.cotemig.homepets.databinding.ActivityDonoAddPetBinding
import br.com.cotemig.homepets.models.PetModel
import br.com.cotemig.homepets.models.TokenModelResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.Constantes
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonoAddPetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonoAddPetBinding
    private var radioGroupSexo : RadioGroup? = null
    private var radioGroupTipo : RadioGroup? = null
    private lateinit var radioButtonSexo: RadioButton
    private lateinit var radioButtonTipo: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonoAddPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        radioGroupSexo = binding.radioSexo
        radioGroupTipo = binding.radioTipo

        binding.btnSalvarPet.setOnClickListener {
           if(validaCampo()){
               /* SALVAR PET DE ACORDO COM DONO */
               addNovoPet()
           }
        }

    }

    private fun addNovoPet(){

        var email = SharedPreferenceHelper.readString(this@DonoAddPetActivity,"userpreferences","email","")
        var token = SharedPreferenceHelper.readString(this@DonoAddPetActivity,"userpreferences","token","")
        var nome = binding.inputNomepet.text.toString()
        var raca = binding.inputRacapet.text.toString()

        var sexo = when(sexoPet()){
            Constantes.Feminino() -> Constantes.Feminino()
            else -> Constantes.Masculino()
        }

        var tipo = when(tipoPet()){
            Constantes.Cachorro() -> 1
            else -> 2
        }

        var petModel = PetModel(nome,raca,sexo,tipo)

        RetrofitInitializer().serviceAPI().createPet(token= "Bearer $token",petModel).enqueue(object : Callback<Void>{

            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                response?.let {
                    if(it.code() == 200){
                        MaterialDialog.Builder(this@DonoAddPetActivity).theme(Theme.LIGHT).title("Sucesso").content("Pet Cadastrado com Sucesso").positiveText("Ok").show()
                        finish()
                    }else{
                        MaterialDialog.Builder(this@DonoAddPetActivity).theme(Theme.LIGHT).title("Erro").content(it.errorBody()!!.string()).positiveText("Ok").show()
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                MaterialDialog.Builder(this@DonoAddPetActivity).theme(Theme.LIGHT).title("Erro").content("API Fora do AR").positiveText("Ok").show()
            }

        })

    }

    private fun validaCampo() : Boolean{

        var validacao = true

        if(binding.inputNomepet.text.toString().isEmpty()){
            binding.inputNomepet.setError("Informe nome do pet")
            validacao = false
        }

        if(binding.inputRacapet.text.toString().isEmpty()){
            binding.inputRacapet.setError("Informe a Raca")
            validacao = false
        }

        if(sexoPet() == null){
            binding.radiobtnFeminino.setError("Informe o Sexo")
            validacao = false
        }else{
            binding.radiobtnFeminino.setError(null)
        }

        if(tipoPet() == null){
            binding.radiobtnCachorro.setError("Informe o Tipo de Pet")
            validacao = false
        }else{
            binding.radiobtnCachorro.setError(null)
        }


        return validacao
    }

    private fun sexoPet() : String? {
        val selectedOption : Int = radioGroupSexo!!.checkedRadioButtonId
        if(findViewById<RadioGroup>(selectedOption) != null){
            radioButtonSexo = findViewById(selectedOption)
            return radioButtonSexo.text.toString()
        }
        return null
    }

    private fun tipoPet() : String? {
        val selectedOption : Int = radioGroupTipo!!.checkedRadioButtonId
        if(findViewById<RadioGroup>(selectedOption) != null){
            radioButtonTipo = findViewById(selectedOption)
            return radioButtonTipo.text.toString()
        }
        return null
    }

}