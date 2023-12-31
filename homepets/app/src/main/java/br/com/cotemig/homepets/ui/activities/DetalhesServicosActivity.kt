package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityDetalhesServicosBinding
import br.com.cotemig.homepets.models.ServiceModel
import br.com.cotemig.homepets.models.ServicesResponse
import br.com.cotemig.homepets.models.TokenModelResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.Constantes
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetalhesServicosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesServicosBinding
    private var radioGroup : RadioGroup? = null
    private lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesServicosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        radioGroup = binding.radioTipoPreco

        getDetalhes()

        binding.btnVoltar.setOnClickListener {
            finish()
        }

        binding.btnSalvar.setOnClickListener {
            if(validaCampos()){
                updateServices()
            }

        }

        binding.btnDeletar.setOnClickListener {
            deleteService()
        }

    }

    private fun getDetalhes(){

        var services = intent.getSerializableExtra("objeto") as ServicesResponse

        binding.inputNomeServico.setText(services.nomeServico)
        binding.inputPrecoServico.setText(binding.inputPrecoServico.formatCurrency((services.preco * 100).toLong()))

        when(services.tipoPreco){
            1 -> binding.radiobtnHora.isChecked = true
            2 -> binding.radiobtnDiaria.isChecked = true
            3 -> binding.radiobtnFechado.isChecked = true
        }

    }

    private fun updateServices(){

        var token = SharedPreferenceHelper.readString(this,"userpreferences","token","")
        var services = intent.getSerializableExtra("objeto") as ServicesResponse

        services.nomeServico = binding.inputNomeServico.text.toString()
        services.preco = binding.inputPrecoServico.text.toString().replace("$","").replace(",","").toDouble()

        services.tipoPreco = when(tipoPreco()){
            Constantes.Hora() -> 1
            Constantes.Diaria() -> 2
            else -> 3
        }


        var servicesModel = ServiceModel(services.id,services.nomeServico,services.preco,services.tipoPreco)


        /* RETROFIT AQUI */
        RetrofitInitializer().serviceAPI().createService(token= "Bearer $token",servicesModel).enqueue(object : Callback<Void>{

            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                response?.let {
                    if(it.code() == 200){
                        MaterialDialog(this@DetalhesServicosActivity).show {
                            title(R.string.sucesso)
                            message(R.string.servicoalterado)
                            positiveButton(R.string.ok){
                                finish()
                            }
                        }


                    }else{
                        MaterialDialog(this@DetalhesServicosActivity).show {
                            title(R.string.erro)
                            message(null,it.errorBody()!!.string())
                            positiveButton(null,"Ok")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                MaterialDialog(this@DetalhesServicosActivity).show {
                    title(R.string.erro)
                    message(null,"API FORA DO AR")
                    positiveButton(null,"Ok")
                }
            }

        })


    }

    private fun deleteService(){
        var token = SharedPreferenceHelper.readString(this,"userpreferences","token","")
        var services = intent.extras!!.get("objeto") as ServicesResponse

        RetrofitInitializer().serviceAPI().deleteService(token= "Bearer $token",services.id).enqueue(object : Callback<Void>{

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                response?.let {
                    if(it.code() == 200){
                        MaterialDialog(this@DetalhesServicosActivity).show {
                            title(R.string.sucesso)
                            message(R.string.servicodeletado)
                            positiveButton(R.string.ok){
                                finish()
                            }
                        }

                    }else{
                        MaterialDialog(this@DetalhesServicosActivity).show {
                            title(R.string.erro)
                            message(null,it.errorBody()!!.string())
                            positiveButton(null,"Ok")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                MaterialDialog(this@DetalhesServicosActivity).show {
                    title(R.string.erro)
                    message(null,"API FORA DO AR")
                    positiveButton(null,"Ok")
                }
            }

        })

    }

    private fun validaCampos() : Boolean{

        var validacao = true

        if(binding.inputNomeServico.text.toString().isEmpty()){
            binding.inputNomeServico.setError("Informe nome do Servico")
            validacao = false
        }
        if(binding.inputPrecoServico.text.toString() == "$0.00"){
            binding.inputPrecoServico.setError("Informe um Valor")
            validacao = false
        }
        return validacao
    }

    private fun tipoPreco() : String?{
        val selectedOption : Int = radioGroup!!.checkedRadioButtonId
        if(findViewById<RadioGroup>(selectedOption) != null){
            radioButton = findViewById(selectedOption)
            return radioButton.text.toString()
        }
        return null
    }

}