package br.com.cotemig.homepets.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.FragmentBuscaServicosBinding

class BuscaServicosFragment : Fragment() {

    private lateinit var binding: FragmentBuscaServicosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscaServicosBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}