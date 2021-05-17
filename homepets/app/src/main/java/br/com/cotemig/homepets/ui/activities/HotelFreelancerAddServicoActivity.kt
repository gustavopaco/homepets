package br.com.cotemig.homepets.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityHotelFreelancerAddServicoBinding
import br.com.cotemig.homepets.models.ServiceModel
import br.com.cotemig.homepets.models.TokenModelResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.Constantes
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
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
        radioGroup = binding.radioTipoPreco

        binding.btnSalvarServico.setOnClickListener {

            if(validaCampos()){
                addNovoServico()
            }
        }

    }

    private fun addNovoServico(){

        var token = SharedPreferenceHelper.readString(this,"userpreferences","token","")
        var nomeServico = binding.inputNomeServico.text.toString()
        var preco = binding.inputPrecoServico.text.toString().replace("$", "").replace(",", "")

        var tipoPreco = when(tipoPreco()){
            Constantes.Hora() -> 1
            Constantes.Diaria() -> 2
            else -> 3
        }


        var novoServico = ServiceModel(0, nomeServico, preco.toDouble(), tipoPreco)

        RetrofitInitializer().serviceAPI().createService(token= "Bearer $token",novoServico).enqueue(object : Callback<Void>{
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                response?.let {
                    if(it.code() == 200){
                        MaterialDialog(this@HotelFreelancerAddServicoActivity).show {
                            title(R.string.sucesso)
                            message(R.string.servicocadastrado)
                            positiveButton(R.string.ok){
                                finish()
                            }
                        }

                    }else{
                        MaterialDialog(this@HotelFreelancerAddServicoActivity).show {
                            title(R.string.erro)
                            message(null,it.errorBody()!!.string())
                            positiveButton(null,"Ok")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                MaterialDialog(this@HotelFreelancerAddServicoActivity).show {
                    title(R.string.erro)
                    message(null,"API FORA DO AR")
                    positiveButton(null,"Ok")
                }
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

        var validacao = true

        if(binding.inputNomeServico.text.toString().isEmpty()){
            binding.inputNomeServico.setError("Informe o Nome do Servico")
            validacao = false
        }
        if(binding.inputPrecoServico.text.toString() == "$0.00"){
            binding.inputPrecoServico.setError("Informe o Preco do Servico")
            validacao = false
        }
        if(tipoPreco() == null){
            binding.radiobtnHora.setError("Informe o Tipo de Preco")
            validacao = false
        }else{
            binding.radiobtnHora.setError(null)
        }

        return validacao

    }

    private fun statsBackground(){
        var stats = SharedPreferenceHelper.readInt(this,"userpreferences","stats",-1)

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