package br.com.cotemig.homepets.ui.activities


import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityRegisterBinding
import br.com.cotemig.homepets.models.Pessoa
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        radioGroup = findViewById(R.id.btn_registerStatus)


        binding.btnRegisterSalvar.setOnClickListener {

            if(validaCampos()){

                var p = Pessoa()

                /* SETANDO VALORES NO OBJETO */
                p.setNome(binding.txtRegisterNome.text.toString())
                p.setemail(binding.txtRegisterMail.text.toString())
                p.setsenha(binding.txtRegisterPassword.text.toString())

                if(tipoUsuario().equals("Dono de Pet",true)){
                    p.setTipoUsuario(1)
                }else if(tipoUsuario().equals("Dono de Hotel",true)){
                        p.setTipoUsuario(2)
                    }else{
                        p.setTipoUsuario(3)
                }

                Toast.makeText(this,"Nome: " + p.getNome() + "\nE-mail: " + p.getemail() + "\nSenha: " + p.getsenha() + "\nTipo Usuario: " + p.getTipoUsuario(),Toast.LENGTH_LONG).show()

            }

        }
    }

    private fun tipoUsuario(): String? {
        val selectedOption: Int = radioGroup!!.checkedRadioButtonId
        if (findViewById<RadioGroup>(selectedOption) != null) {
            radioButton = findViewById(selectedOption)
            return radioButton.text.toString()
        } else {
            return null
        }

    }

    private fun validaCampos(): Boolean {

        var imprimeCampos = ""
        var validacao = true

        if (binding.txtRegisterNome.text.toString().isEmpty()) {
            imprimeCampos += "Nome\n"
            validacao = false
        }

        if (binding.txtRegisterMail.text.toString().isEmpty()) {
            imprimeCampos += "Email\n"
            validacao = false
        }

        if (binding.txtRegisterPassword.text.toString().isNotEmpty() && binding.txtRegisterRepeatPassword.text.toString().isNotEmpty()) {

            if (!binding.txtRegisterPassword.text.toString().equals(binding.txtRegisterRepeatPassword.text.toString(), true)) {
                MaterialDialog.Builder(this).theme(Theme.LIGHT).title("Erro")
                    .content("Senha e Confirmacao de senha, nao correspodem").positiveText("Ok")
                    .show()
                validacao = false
            }
        } else {
            imprimeCampos += "Senha e/ou Confirmacao de Senha\n"
            validacao = false
        }

        if (tipoUsuario() == null) {
            imprimeCampos += "Tipo de Usuario\n"
            validacao = false
        }
        if (imprimeCampos.isNotEmpty()) {
            var notificacao = "Preencha os campos:\n$imprimeCampos"
            MaterialDialog.Builder(this).theme(Theme.LIGHT).title("Erro")
                .content(notificacao).positiveText("Ok").show()
            validacao = false
        }
        return validacao
    }
}