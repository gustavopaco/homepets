package br.com.cotemig.homepets.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.databinding.ActivityLoginUserBinding
import br.com.cotemig.homepets.models.Pessoa
import br.com.cotemig.homepets.services.RetrofitInitializer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if(validaCampo()){
                getLogin()
            }
        }

        binding.btnRegistrar.setOnClickListener {
            goRegisterActivity()
        }

    }

    private fun getLogin(){

        var pessoa = Pessoa()

        pessoa.setemail(binding.txtLogin.text.toString())
        pessoa.setsenha(binding.txtPassword.text.toString())

        RetrofitInitializer().serviceAPI().getAuth(pessoa).enqueue(object  : Callback<Pessoa>{
            override fun onResponse(call: Call<Pessoa>, response: Response<Pessoa>) {
                response?.let {
                    if(it.code() == 200){
                        Toast.makeText(this@LoginUserActivity,"API FUNCIONANDO",Toast.LENGTH_LONG).show()
                        Log.d("STATE","TOKEN:\n" + it.body()!!.getToken())
                    }else{
                        Toast.makeText(this@LoginUserActivity,"API NAO FUNCIONANDO" + it.code(),Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Pessoa>, t: Throwable) {
                Toast.makeText(this@LoginUserActivity,"API FORA DO AR",Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun validaCampo() : Boolean{

        var imprime = ""
        var validacao = true

        if(binding.txtLogin.text.toString().isEmpty()){
            imprime += "\nLogin"
            validacao = false
        }

        if(binding.txtPassword.text.toString().isEmpty()){
            imprime += "\nSenha"
            validacao = false
        }

        if(imprime.isNotEmpty()){
            var notificacao = "Preencha os campos: $imprime"
            MaterialDialog.Builder(this).theme(Theme.LIGHT).title("Error").content(notificacao).positiveText("Ok").show()
        }

        return validacao

    }

    private fun goRegisterActivity(){
        var intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }

}