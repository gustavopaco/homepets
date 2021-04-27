package br.com.cotemig.homepets.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.FragmentPerfilBinding

class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

}