package br.com.cotemig.homepets.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cotemig.homepets.databinding.FragmentDonoBuscaServicosBinding

class DonoBuscaServicosFragment : Fragment() {

    private lateinit var binding: FragmentDonoBuscaServicosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDonoBuscaServicosBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}