package br.com.cotemig.homepets.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.homepets.databinding.ItemHotelFreelancerMeusservicosBinding
import br.com.cotemig.homepets.models.ServiceModel
import br.com.cotemig.homepets.models.ServicesResponse
import br.com.cotemig.homepets.util.Constantes

class MeusServicosAdapter(var context: Context, var list: List<ServicesResponse>?) : RecyclerView.Adapter<MeusServicosAdapter.ViewServices>(){

    private lateinit var binding: ItemHotelFreelancerMeusservicosBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewServices {
        binding = ItemHotelFreelancerMeusservicosBinding.inflate(LayoutInflater.from(context),parent,false)

        return ViewServices(binding)
    }

    override fun onBindViewHolder(holder: ViewServices, position: Int) {
        holder.bind(context, list!![position])
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class ViewServices(binding: ItemHotelFreelancerMeusservicosBinding) : RecyclerView.ViewHolder(binding.root){

        private var nomeServico = binding.txtNomeServico
        private var precoServico = binding.txtPrecoServico
        private var tipoPreco = binding.txtTipoPreco
        private var currency = binding.inputCurrency

        fun bind(context: Context, item : ServicesResponse){

            nomeServico.text = item.nomeServico
            precoServico.text = currency.formatCurrency((item.preco*100).toLong()).toString()

            when(item.tipoPreco){
                1 -> tipoPreco.text = Constantes.Hora()
                2 -> tipoPreco.text = Constantes.Diaria()
                3 -> tipoPreco.text = Constantes.Fechado()
            }
        }
    }
}