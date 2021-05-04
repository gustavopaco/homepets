package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityDonoAddPetBinding
import br.com.cotemig.homepets.models.PetModel
import br.com.cotemig.homepets.models.TokenModelResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.Constantes
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
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
                        MaterialDialog(this@DonoAddPetActivity).show {
                            title(R.string.sucesso)
                            message(R.string.petcadastrado)
                            positiveButton(R.string.ok){
                                finish()
                            }
                        }

                    }else{
                        MaterialDialog(this@DonoAddPetActivity).show {
                            title(R.string.erro)
                            message(null,it.errorBody()!!.string())
                            positiveButton(null,"Ok")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                MaterialDialog(this@DonoAddPetActivity).show {
                    title(R.string.erro)
                    message(null,"API FORA DO AR")
                    positiveButton(null,"Ok")
                }
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