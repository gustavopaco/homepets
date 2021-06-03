package br.com.cotemig.homepets.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.homepets.databinding.ActivityDonoCreditCardBinding

class DonoCreditCardActivity : AppCompatActivity(){

    private lateinit var binding: ActivityDonoCreditCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonoCreditCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNovoCartao.setOnClickListener {
            goAddNovoCartao()
        }

    }

    private fun goAddNovoCartao() {
       startActivity(Intent(this,DonoAddCreditCardActivity::class.java))
    }
}