package br.com.cotemig.homepets.ui.fragments

import android.content.Intent
import android.content.res.Resources
import android.graphics.ColorFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.FragmentHotelFreelancerServicosBinding
import br.com.cotemig.homepets.ui.activities.HomeActivity
import br.com.cotemig.homepets.ui.activities.HotelFreelancerAddServicoActivity
import br.com.cotemig.homepets.util.SharedPreferenceHelper

class HotelFreelancerServicosFragment : Fragment() {

    private lateinit var binding: FragmentHotelFreelancerServicosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelFreelancerServicosBinding.inflate(layoutInflater,container,false)
        freeColors()



        binding.addNovoServico.setOnClickListener {
            goAddNovoServico()
        }

        return binding.root
    }

    private fun goAddNovoServico(){
        var activity = context as HomeActivity
        startActivity(Intent(activity,HotelFreelancerAddServicoActivity::class.java))
    }

    private fun freeColors(){
        var activity = context as HomeActivity
        var stats = SharedPreferenceHelper.readInt(activity,"userpreferences","stats",-1)

        if(stats == 3){
            binding.addNovoServico.imageTintList = ContextCompat.getColorStateList(activity,R.color.cyan)
        }
    }
}