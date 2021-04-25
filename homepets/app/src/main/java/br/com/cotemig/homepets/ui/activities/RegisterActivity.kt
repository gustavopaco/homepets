package br.com.cotemig.homepets.ui.activities


import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityRegisterBinding
import br.com.cotemig.homepets.models.Pessoa
import br.com.cotemig.homepets.models.RegisterResponse
import br.com.cotemig.homepets.models.UserAPI
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

        var tipo = 0

        if(tipoUsuario().equals("Dono de Pet",true)){
            pessoa.setTipoUsuario(1)
            tipo = 1
        }else if(tipoUsuario().equals("Dono de Hotel",true)){
            pessoa.setTipoUsuario(2)
            tipo = 2
        }else{
            pessoa.setTipoUsuario(3)
            tipo = 3
        }

        var nome = binding.txtRegisterNome.text.toString()
        var email = binding.txtRegisterMail.text.toString()
        var senha = binding.txtRegisterPassword.text.toString()

        var userAPI = UserAPI(nome,email,senha,tipo)


        /* RETROFIT - CONECTANDO COM API */
        RetrofitInitializer().serviceAPI().createUser(userAPI)
            .enqueue(object: Callback<RegisterResponse>{

                override fun onResponse(call: Call<RegisterResponse>?,response: Response<RegisterResponse>?) {
                    response.let {
                        if(it!!.code() == 200){
                                /* MOSTRAR MENSAGEM DE USUARIO CADASTRADO COM SUCESSO */
                                    Log.d("STATE","TOKEN: \n" + it.body()!!.token)
                            finish() // MATANDO ACTIVITY DE CADASTRO APOS REGISTRAR E VOLTANDO PARA TELA DE LOGIN
                        }else{
                            Toast.makeText(this@RegisterActivity,"Erro Codigo: " + it.code(),Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                    Toast.makeText(this@RegisterActivity,"API NAO RESPONDE",Toast.LENGTH_LONG).show()
                }
            })


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