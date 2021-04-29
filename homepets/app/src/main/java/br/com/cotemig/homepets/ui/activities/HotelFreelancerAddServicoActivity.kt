package br.com.cotemig.homepets.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.databinding.ActivityHotelFreelancerAddServicoBinding
import br.com.cotemig.homepets.models.ServiceModel
import br.com.cotemig.homepets.models.TokenModelResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.Constantes
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelFreelancerAddServicoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHotelFreelancerAddServicoBinding
    private var radioGroup: RadioGroup? = null
    private lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelFreelancerAddServicoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statsBackground()
        radioGroup = binding.radioTipoServico

        binding.btnSalvarServico.setOnClickListener {

            if(validaCampos()){
                addNovoServico()
            }
        }

    }

    private fun addNovoServico(){

        var email = SharedPreferenceHelper.readString(
            this@HotelFreelancerAddServicoActivity,
            "userpreferences",
            "email",
            ""
        )
        var nomeServico = binding.inputNomeServico.text.toString()
        var preco = binding.inputPrecoServico.text.toString().replace("$", "").replace(",", "")

        var tipoPreco = when(tipoPreco()){
            Constantes.Hora() -> 1
            Constantes.Diaria() -> 2
            else -> 3
        }


        var novoServico = ServiceModel(email.toString(), nomeServico, preco.toDouble(), tipoPreco)

        RetrofitInitializer().serviceAPI().createService(novoServico).enqueue(object : Callback<TokenModelResponse>{
            override fun onResponse(
                call: Call<TokenModelResponse>,
                response: Response<TokenModelResponse>
            ) {
                response?.let {
                    if(it.code() == 200){
                        MaterialDialog.Builder(this@HotelFreelancerAddServicoActivity).theme(Theme.LIGHT).title("Sucesso").content("Novo Serviço cadastrado!").positiveText("Ok").show()
                        finish()
                    }else{
                        MaterialDialog.Builder(this@HotelFreelancerAddServicoActivity).theme(Theme.LIGHT).title("Erro").content(it.errorBody()!!.string()).positiveText("Ok").show()
                    }
                }
            }

            override fun onFailure(call: Call<TokenModelResponse>, t: Throwable) {
                MaterialDialog.Builder(this@HotelFreelancerAddServicoActivity).theme(Theme.LIGHT).title("Erro").content("API fora do AR").positiveText("Ok").show()
            }

        })

    }

    private fun tipoPreco(): String? {
        val selectedOption: (Int) = radioGroup!!.checkedRadioButtonId
        if (findViewById<RadioGroup>(selectedOption) != null) {
            radioButton = findViewById(selectedOption)
            return radioButton.text.toString()
        }
        return null
    }

    private fun validaCampos() : Boolean{

        var imprime = ""
        var validacao = true

        if(binding.inputNomeServico.text.toString().isEmpty()){
            imprime += "Nome do Serviço\n"
            validacao = false
        }
        if(binding.inputPrecoServico.text.toString() == "$0.00"){
            imprime += "Preço\n"
            validacao = false
        }
        if(tipoPreco() == null){
            imprime += "Tipo de Serviço\n"
            validacao = false
        }

        if(imprime.isNotEmpty()){
            var notificacao = "Preencha os Campos:\n$imprime"
            MaterialDialog.Builder(this).theme(Theme.LIGHT).title("Erro").content(notificacao).positiveText(
                "Ok"
            ).show()
            validacao = false
        }
        return validacao

    }

    private fun statsBackground(){
        var stats = 2
//            SharedPreferenceHelper.readInt(this,"userpreferences","stats",-1)

        when(stats){
            2 ->{
                binding.imgHotelbg2.visibility = View.VISIBLE
                binding.imgHotelbg3.visibility = View.VISIBLE
            }
            3-> {
                binding.imgFreelancerbg1.visibility = View.VISIBLE
                binding.imgFreelancerbg2.visibility = View.VISIBLE
                binding.imgFreelancerbg3.visibility = View.VISIBLE
                binding.imgFreelancerbg4.visibility = View.VISIBLE
            }
        }
    }

}