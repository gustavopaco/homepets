package br.com.cotemig.homepets.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.FragmentHotelReservasBinding
import br.com.cotemig.homepets.ui.activities.DonoHistoricoServicosActivity
import br.com.cotemig.homepets.ui.activities.HomeActivity
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class HotelReservasFragment : Fragment() {

    private lateinit var binding: FragmentHotelReservasBinding
    private val reservas = DonoHistoricoServicosActivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelReservasBinding.inflate(layoutInflater,container,false)

        var activity = context as HomeActivity
        reservas.getHistorico(activity,binding.listaHotelReservasServicos)

        swipe(activity,binding.listaHotelReservasServicos)

        return binding.root
    }

    private fun swipe(context : Context, recyclerView: RecyclerView) {
        binding.swipe.setOnRefreshListener {
            reservas.getHistorico(context,recyclerView)
            Executors.newSingleThreadScheduledExecutor().schedule({
                binding.swipe.isRefreshing = false
            },1, TimeUnit.SECONDS)
        }
    }

}