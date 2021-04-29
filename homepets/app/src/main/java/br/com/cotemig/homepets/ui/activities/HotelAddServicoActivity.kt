package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import br.com.cotemig.homepets.databinding.ActivityHotelAddServicoBinding
import br.com.cotemig.homepets.util.Constantes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme

class HotelAddServicoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHotelAddServicoBinding
    private var radioGroup: RadioGroup? = null
    private lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelAddServicoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        radioGroup = binding.radioTipoServico

        binding.btnSalvarServico.setOnClickListener {
            if(validaCampos()){
                addNovoServico()
            }
        }

    }

    private fun addNovoServico(){

        var nomeServico = binding.inputNomeServico.text.toString()
        var preco = binding.inputPrecoServico.text.toString().replace("$","").replace(",","")

        var tipoPreco = when(tipoPreco()){
            Constantes.Hora() -> 1
            Constantes.Diaria() -> 2
            else -> 3
        }
        

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
            MaterialDialog.Builder(this).theme(Theme.LIGHT).title("Erro").content(notificacao).positiveText("Ok").show()
            validacao = false
        }
        return validacao

    }

}