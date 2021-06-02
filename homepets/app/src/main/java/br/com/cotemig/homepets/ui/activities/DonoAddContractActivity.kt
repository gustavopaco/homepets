package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.cotemig.homepets.databinding.ActivityDonoAddContractBinding

class DonoAddContractActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonoAddContractBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonoAddContractBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}