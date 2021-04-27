package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.cotemig.homepets.databinding.ActivityDonoAddPetBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme

class DonoAddPetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonoAddPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonoAddPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSalvarPet.setOnClickListener {
           if(validaCampo()){
               /* SALVAR PET DE ACORDO COM DONO */
               addNovoPet()
           }
        }

    }

    private fun addNovoPet(){
        var nome = binding.inputNomepet.text.toString()
        var raca = binding.inputRacapet.text.toString()

        /* RETROFIT AQUI*/

        finish()
    }

    private fun validaCampo() : Boolean{

        var imprime = ""
        var validacao = true

        if(binding.inputNomepet.text.toString().isEmpty()){
            imprime += "Nome do Pet\n"
            validacao = false
        }

        if(binding.inputRacapet.text.toString().isEmpty()){
            imprime += "Raça do Pet\n"
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