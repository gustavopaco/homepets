package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import br.com.cotemig.homepets.databinding.ActivityDetalhesServicosBinding
import br.com.cotemig.homepets.models.ServiceModel
import br.com.cotemig.homepets.models.ServicesResponse
import br.com.cotemig.homepets.models.TokenModelResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.Constantes
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
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

        var services = intent.extras!!.get("objeto") as ServicesResponse

        binding.inputNomeServico.setText(services.nomeServico)
        binding.inputPrecoServico.setText(binding.inputPrecoServico.formatCurrency((services.preco * 100).toLong()))

        when(services.tipoPreco){
            1 -> binding.radiobtnHora.isChecked = true
            2 -> binding.radiobtnDiaria.isChecked = true
            3 -> binding.radiobtnFechado.isChecked = true
        }

    }

    private fun updateServices(){

        var email = SharedPreferenceHelper.readString(this,"userpreferences","email","")
        var services = intent.extras!!.get("objeto") as ServicesResponse

        services.nomeServico = binding.inputNomeServico.text.toString()
        services.preco = binding.inputPrecoServico.text.toString().replace("$","").replace(",","").toDouble()

        services.tipoPreco = when(tipoPreco()){
            Constantes.Hora() -> 1
            Constantes.Diaria() -> 2
            else -> 3
        }


        var servicesModel = ServiceModel(email.toString(),services.id,services.nomeServico,services.preco,services.tipoPreco)


        /* RETROFIT AQUI */
        RetrofitInitializer().serviceAPI().updateService(servicesModel).enqueue(object : Callback<TokenModelResponse>{

            override fun onResponse(
                call: Call<TokenModelResponse>,
                response: Response<TokenModelResponse>
            ) {
                response?.let {
                    if(it.code() == 200){
                        MaterialDialog.Builder(this@DetalhesServicosActivity).theme(Theme.LIGHT).title("Sucesso").content("Dados do Servico Alterado!").positiveText("Ok").show()
                        finish()
                    }else{
                        MaterialDialog.Builder(this@DetalhesServicosActivity).theme(Theme.LIGHT).title("Erro").content(it.errorBody()!!.string()).positiveText("Ok").show()
                    }
                }
            }

            override fun onFailure(call: Call<TokenModelResponse>, t: Throwable) {
                MaterialDialog.Builder(this@DetalhesServicosActivity).theme(Theme.LIGHT).title("Erro").content("API fora do AR").positiveText("Ok").show()
            }

        })


    }

    private fun deleteService(){
        var email = SharedPreferenceHelper.readString(this,"userpreferences","email","")
        var services = intent.extras!!.get("objeto") as ServicesResponse

        RetrofitInitializer().serviceAPI().deleteService(email.toString(),services.id).enqueue(object : Callback<Void>{

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                response?.let {
                    if(it.code() == 204){
                        MaterialDialog.Builder(this@DetalhesServicosActivity).theme(Theme.LIGHT).title("Sucesso").content("Servico Deletado!").positiveText("Ok").show()
                        finish()
                    }else{
                        MaterialDialog.Builder(this@DetalhesServicosActivity).theme(Theme.LIGHT).title("Erro").content(it.errorBody()!!.string()).positiveText("Ok").show()
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                MaterialDialog.Builder(this@DetalhesServicosActivity).theme(Theme.LIGHT).title("Erro").content("API fora do AR").positiveText("Ok").show()
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