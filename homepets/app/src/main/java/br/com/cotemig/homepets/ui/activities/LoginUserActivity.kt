package br.com.cotemig.homepets.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.databinding.ActivityLoginUserBinding

class LoginUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            
        }

        binding.btnRegistrar.setOnClickListener {
            goRegisterActivity()
        }

    }

    private fun goRegisterActivity(){
        var intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }

}