package br.com.cotemig.homepets.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cotemig.homepets.databinding.FragmentDonoMeusPetsBinding


class DonoMeusPetsFragment : Fragment() {

    private lateinit var binding: FragmentDonoMeusPetsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDonoMeusPetsBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

}