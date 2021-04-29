package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import br.com.cotemig.homepets.databinding.ActivityHotelAddServicoBinding

class HotelAddServicoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHotelAddServicoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelAddServicoBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }


}