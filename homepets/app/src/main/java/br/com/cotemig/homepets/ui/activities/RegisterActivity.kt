package br.com.cotemig.homepets.ui.activities


import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.R
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        radioGroup = findViewById(R.id.btn_registerStatus)


        btn_registerSalvar.setOnClickListener {
            if(validaCampos()){
                // CADASTRAR USUARIO
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
        var nome = findViewById<EditText>(R.id.txt_registerNome)
        var mail = findViewById<EditText>(R.id.txt_registerMail)
        var senha1 = findViewById<EditText>(R.id.txt_registerPassword)
        var senha2 = findViewById<EditText>(R.id.txt_registerRepeatPassword)

        var imprimeCampos = ""
        var validacao = true

        if (nome.text.toString().isEmpty()) {
            imprimeCampos += "Nome\n"
            validacao = false
        }

        if (mail.text.toString().isEmpty()) {
            imprimeCampos += "Email\n"
            validacao = false
        }

        if (senha1.text.toString().isNotEmpty() && senha2.text.toString().isNotEmpty()) {

            if (!senha1.text.toString().equals(senha2.text.toString(), true)) {
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