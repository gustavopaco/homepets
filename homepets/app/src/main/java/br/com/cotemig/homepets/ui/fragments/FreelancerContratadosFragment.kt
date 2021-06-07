package br.com.cotemig.homepets.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.homepets.databinding.FragmentFreelancerContratadosBinding
import br.com.cotemig.homepets.ui.activities.DonoHistoricoServicosActivity
import br.com.cotemig.homepets.ui.activities.HomeActivity
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class FreelancerContratadosFragment : Fragment() {

    private lateinit var binding: FragmentFreelancerContratadosBinding
    private val contratados = DonoHistoricoServicosActivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFreelancerContratadosBinding.inflate(layoutInflater,container,false)

        val context = context as HomeActivity
        contratados.getHistorico(context,binding.listaFreelancerContratadosServicos)

        swipe(context,binding.listaFreelancerContratadosServicos)

        return binding.root
    }

    private fun swipe(context: Context,recyclerView: RecyclerView) {
        binding.swipe.setOnRefreshListener {
            contratados.getHistorico(context,recyclerView)
            Executors.newSingleThreadScheduledExecutor().schedule({
                binding.swipe.isRefreshing = false
            },1,TimeUnit.SECONDS)
        }
    }

}