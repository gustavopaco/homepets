package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.cotemig.homepets.databinding.ActivityDetalhesCreditCardBinding

class DetalhesCreditCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesCreditCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesCreditCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}