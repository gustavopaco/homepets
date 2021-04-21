package br.com.cotemig.homepets.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.cotemig.homepets.R
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Executors.newSingleThreadScheduledExecutor().schedule({
            goHomeActivity()
        },5,TimeUnit.SECONDS)

    }

    fun goHomeActivity(){
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}