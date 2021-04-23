package br.com.cotemig.homepets.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.R
import kotlinx.android.synthetic.main.activity_login_user.*

class LoginUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)

        btn_registrar.setOnClickListener {
            goRegisterActivity()
        }

    }

    fun goRegisterActivity(){
        var intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }

}