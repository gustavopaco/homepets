package br.com.cotemig.homepets.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivitySplashBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Executors.newSingleThreadScheduledExecutor().schedule({
            goHomeActivity()
        },5,TimeUnit.SECONDS)

    }

    private fun goHomeActivity(){
        var intent = Intent(this, LoginUserActivity::class.java)
        startActivity(intent)
        finish()
    }

}