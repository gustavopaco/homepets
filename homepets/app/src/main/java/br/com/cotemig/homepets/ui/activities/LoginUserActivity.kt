package br.com.cotemig.homepets.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.databinding.ActivityLoginUserBinding
import br.com.cotemig.homepets.models.AuthModel
import br.com.cotemig.homepets.models.RegisterModel
import br.com.cotemig.homepets.models.TokenModelResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.SharedPreferenceHelper
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


        var email = binding.txtLogin.text.toString()
        var senha = binding.txtPassword.text.toString()

        var userAPI = AuthModel(email,senha)

        RetrofitInitializer().serviceAPI().getAuth(userAPI).enqueue(object  : Callback<TokenModelResponse>{

            override fun onResponse(call: Call<TokenModelResponse>, response: Response<TokenModelResponse>) {
                response?.let {
                    if(it.code() == 200){
                        saveAccountInfo(it.body()!!.token, it.body()!!.stats)
                        goHomeActivity()
                    }else{
                        MaterialDialog.Builder(this@LoginUserActivity).title("Erro").content(it.errorBody()!!.string()).positiveText("Ok").show()
                    }
                }
            }

            override fun onFailure(call: Call<TokenModelResponse>, t: Throwable) {
                MaterialDialog.Builder(this@LoginUserActivity).title("Erro").content("API FORA DO AR").positiveText("Ok").show()
            }

        })

    }

    private fun saveAccountInfo(token: String, stats: Int){
        SharedPreferenceHelper.saveString(this@LoginUserActivity,"userpreferences","email",binding.txtLogin.text.toString())
        SharedPreferenceHelper.saveString(this@LoginUserActivity,"userpreferences","senha",binding.txtPassword.text.toString())
        SharedPreferenceHelper.saveString(this@LoginUserActivity,"userpreferences","token",token)
        SharedPreferenceHelper.saveInt(this@LoginUserActivity,"userpreferences","stats",stats)
    }

    private fun validaCampo() : Boolean{

        var validacao = true

        if(binding.txtLogin.text.toString().isEmpty()){
            binding.txtLogin.setError("Informe seu E-mail")
            validacao = false
        }

        if(binding.txtPassword.text.toString().isEmpty()){
            binding.txtPassword.setError("Informe sua senha")
            validacao = false
        }


        return validacao

    }

    private fun goRegisterActivity(){
        startActivity((Intent(this@LoginUserActivity,RegisterActivity::class.java)))

    }

    private fun goHomeActivity(){
        startActivity(Intent(this@LoginUserActivity,HomeActivity::class.java))
    }

}