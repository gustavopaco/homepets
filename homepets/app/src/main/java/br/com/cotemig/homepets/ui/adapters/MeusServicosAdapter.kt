package br.com.cotemig.homepets.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.homepets.databinding.ItemHotelFreelancerMeusservicosBinding
import br.com.cotemig.homepets.models.ServiceModel
import br.com.cotemig.homepets.models.ServicesResponse
import br.com.cotemig.homepets.util.Constantes

class MeusServicosAdapter(var context: Context, var list: List<ServicesResponse>?, val listener : OnItemClickListener) : RecyclerView.Adapter<MeusServicosAdapter.ViewServices>(){

    private lateinit var binding: ItemHotelFreelancerMeusservicosBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewServices {
        binding = ItemHotelFreelancerMeusservicosBinding.inflate(LayoutInflater.from(context),parent,false)

        return ViewServices(binding)
    }

    override fun onBindViewHolder(holder: ViewServices, position: Int) {
        val currentItem = list!![position]
        holder.nomeServico.text = currentItem.nomeServico
        holder.precoServico.text = holder.currency.formatCurrency((currentItem.preco*100).toLong()).toString()
        holder.servicoID.text = currentItem.id.toString()

        when(currentItem.tipoPreco){
            1 -> {
                holder.tipoPreco.text = Constantes.Hora()
                holder.tipoPreco2 = currentItem.tipoPreco
            }
            2 -> {
                holder.tipoPreco.text = Constantes.Diaria()
                holder.tipoPreco2 = currentItem.tipoPreco
            }
            3 -> {
                holder.tipoPreco.text = Constantes.Fechado()
                holder.tipoPreco2 = currentItem.tipoPreco
            }
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    inner class ViewServices(binding: ItemHotelFreelancerMeusservicosBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        val nomeServico = binding.txtNomeServico
        val precoServico = binding.txtPrecoServico
        val tipoPreco = binding.txtTipoPreco
        val currency =  binding.inputCurrency
        val servicoID = binding.servicoID
        var tipoPreco2 = -1

        init {
            binding.root.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                var id : Int = servicoID.text.toString().toInt()
                var nomeServico : String = nomeServico.text.toString()
                var preco : Double = precoServico.text.toString().replace("$","").replace(",","").toDouble()

                val servico = ServicesResponse(id,nomeServico,preco,tipoPreco2)
                listener.OnItemClick(position, servico)
            }
        }
    }

    interface OnItemClickListener{
        fun OnItemClick(position: Int, service : ServicesResponse)
    }
}