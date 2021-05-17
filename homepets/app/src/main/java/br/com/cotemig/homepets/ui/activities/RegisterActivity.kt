package br.com.cotemig.homepets.ui.activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityRegisterBinding
import br.com.cotemig.homepets.models.RegisterModel
import br.com.cotemig.homepets.models.TokenModelResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.Constantes
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
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

            if (validaCampos()) {

                registrarPessoa()
            }

        }
    }

    private fun registrarPessoa() {


        var tipo = when (tipoUsuario()) {
            Constantes.DonoPet() -> 1
            Constantes.DonoHotel() -> 2
            else -> 3
        }

        var nome = binding.txtRegisterNome.text.toString()
        var email = binding.txtRegisterMail.text.toString()
        var senha = binding.txtRegisterPassword.text.toString()

        var userAPI = RegisterModel(nome, email, senha, tipo)


        /* RETROFIT - CONECTANDO COM API */
        RetrofitInitializer().serviceAPI().createUser(userAPI)
            .enqueue(object : Callback<TokenModelResponse> {

                override fun onResponse(
                    call: Call<TokenModelResponse>?,
                    response: Response<TokenModelResponse>?
                ) {
                    response.let {
                        if (it!!.code() == 200) {
                            SharedPreferenceHelper.saveString(this@RegisterActivity,"userpreferences","nome",nome)
                            SharedPreferenceHelper.saveString(this@RegisterActivity,"userpreferences","email",email)
                            SharedPreferenceHelper.saveString(this@RegisterActivity,"userpreferences","senha",nome)
                            SharedPreferenceHelper.saveString(this@RegisterActivity,"userpreferences","token",it.body()!!.token)
                            SharedPreferenceHelper.saveInt(this@RegisterActivity,"userpreferences","stats",tipo)
                            MaterialDialog(this@RegisterActivity).show {
                                title(R.string.sucesso)
                                message(R.string.usuariocadastrado)
                                positiveButton(R.string.ok){
                                    goHomeActvitiy()
                                    finish() // MATANDO ACTIVITY DE CADASTRO APOS REGISTRAR E VOLTANDO PARA TELA DE LOGIN
                                }
                            }

                        } else {
                            MaterialDialog(this@RegisterActivity).show {
                                title(R.string.erro)
                                message(null,it.errorBody()!!.string())
                                positiveButton(null,"Ok")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<TokenModelResponse>?, t: Throwable?) {
                    MaterialDialog(this@RegisterActivity).show {
                        title(R.string.erro)
                        message(null,"API FORA DO AR")
                        positiveButton(null,"Ok")
                    }
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

    private fun goHomeActvitiy(){
        var intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }

    private fun validaCampos(): Boolean {

        var validacao = true

        if (binding.txtRegisterNome.text.toString().isEmpty()) {
            binding.txtRegisterNome.setError("Informe seu Nome")
            validacao = false
        }

        if (binding.txtRegisterMail.text.toString().isEmpty()) {
            binding.txtRegisterMail.setError("Informe seu Email")
            validacao = false
        }

        if (binding.txtRegisterPassword.text.toString().isEmpty()) {
            binding.txtRegisterPassword.setError("Informe a Senha")
            validacao = false
        } else if (binding.txtRegisterPassword.text.toString().length < 5) {
            binding.txtRegisterPassword.setError("Min 5 caracteres")
            validacao = false
        }

        if (binding.txtRegisterRepeatPassword.text.toString().isEmpty()) {
            binding.txtRegisterRepeatPassword.setError("Informe a Senha")
            validacao = false
        } else if (binding.txtRegisterRepeatPassword.text.toString().length < 5) {
            binding.txtRegisterRepeatPassword.setError("Min 5 caracteres")
            validacao = false
        }

        if(binding.txtRegisterPassword.text.toString().length >= 5 && binding.txtRegisterRepeatPassword.text.toString().length >= 5){
            if (!binding.txtRegisterPassword.text.toString().equals(binding.txtRegisterRepeatPassword.text.toString(), false)
            ) {
                MaterialDialog(this).show {
                    title(R.string.erro)
                    message(R.string.senhasdiferentes)
                    positiveButton(R.string.ok)
                }
                validacao = false
            }
        }



        if (tipoUsuario() == null) {
            binding.btnRegisterDono.setError("Selecione um Tipo de Usuario")
            validacao = false
        }else{
            binding.btnRegisterDono.setError(null)
        }

        return validacao
    }
}