package br.com.cotemig.homepets.ui.activities


import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityRegisterBinding
import br.com.cotemig.homepets.models.DefaultResponse
import br.com.cotemig.homepets.models.Pessoa
import br.com.cotemig.homepets.services.RetrofitInitializer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

                registrarPessoa()
            }

        }
    }

    private fun registrarPessoa(){

        var pessoa = Pessoa()

        /* SETANDO VALORES NO OBJETO */
        pessoa.setNome(binding.txtRegisterNome.text.toString())
        pessoa.setemail(binding.txtRegisterMail.text.toString())
        pessoa.setsenha(binding.txtRegisterPassword.text.toString())

        if(tipoUsuario().equals("Dono de Pet",true)){
            pessoa.setTipoUsuario(1)
        }else if(tipoUsuario().equals("Dono de Hotel",true)){
            pessoa.setTipoUsuario(2)
        }else{
            pessoa.setTipoUsuario(3)
        }

        /* DADOS DA INTERFACE */
//        Toast.makeText(this,"Nome: " + pessoa.getNome() + "\nE-mail: " + pessoa.getemail() + "\nSenha: " + pessoa.getsenha() + "\nTipo Usuario: " + pessoa.getTipoUsuario(),Toast.LENGTH_LONG).show()

        /* RETROFIT - CONECTANDO COM API */
//        var s = RetrofitInitializer().serviceAPI().createUser(pessoa.getNome(),pessoa.getemail(),pessoa.getsenha(),pessoa.getTipoUsuario())
//            .enqueue(object: Callback<DefaultResponse>{
//
//                override fun onResponse(call: Call<DefaultResponse>?,response: Response<DefaultResponse>?) {
//                    response.let {
//                        if(it!!.code() == 200){
//                            it.body().message
//                                /* MOSTRAR MENSAGEM DE USUARIO CADASTRADO COM SUCESSO */
//                            finish() // MATANDO ACTIVITY DE CADASTRO APOS REGISTRAR E VOLTANDO PARA TELA DE LOGIN
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<DefaultResponse>?, t: Throwable?) {
//                    /* MENSAGEM DE ERRO, VINDO DA API */
//                }
//            })


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