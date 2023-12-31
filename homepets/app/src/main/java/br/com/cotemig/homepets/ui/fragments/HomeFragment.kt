package br.com.cotemig.homepets.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import br.com.cotemig.homepets.databinding.FragmentHomeBinding
import br.com.cotemig.homepets.ui.activities.DonoHistoricoServicosActivity
import br.com.cotemig.homepets.ui.activities.HomeActivity
import br.com.cotemig.homepets.util.SharedPreferenceHelper

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val historico = DonoHistoricoServicosActivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        var activity = context as HomeActivity
        var stats = SharedPreferenceHelper.readInt(activity, "userpreferences", "stats", -1)
        var nome = SharedPreferenceHelper.readString(activity, "userpreferences", "nome", "")

        binding.headBemvindo.text = binding.headBemvindo.text.toString() + " " + nome.toString()

        when (stats) {
            1 -> {
                binding.homeDono.isVisible = true
                historico.getHistorico(activity,binding.listaDonoHistoricoServicos)
            }
            2 -> binding.homeHotel.isVisible = true
            3 -> binding.homeFreelancer.isVisible = true
        }




        return binding.root
    }


}