package br.com.cotemig.homepets.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.homepets.databinding.ItemDonoBuscaservicosBinding
import br.com.cotemig.homepets.models.ServiceSearchResponse
import br.com.cotemig.homepets.util.Constantes

class ServiceSearchAdapter(var context: Context, var list: List<ServiceSearchResponse>?, val listener: OnItemClickListener) : RecyclerView.Adapter<ServiceSearchAdapter.ViewSearch>(){
    private lateinit var binding: ItemDonoBuscaservicosBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewSearch {
       binding = ItemDonoBuscaservicosBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewSearch(binding)
    }

    override fun onBindViewHolder(holder: ViewSearch, position: Int) {
        holder.bind(context,list!![position])
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    inner class ViewSearch(binding:ItemDonoBuscaservicosBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        init {
            binding.root.setOnClickListener(this)
        }

        private var id_servico = -1
        private var tipo_preco = -1

        override fun onClick(v: View?) {
           val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                var nomeServico = binding.txtNomeServico.text.toString()
                var preco = binding.txtPrecoServico.text.toString().replace("$","").replace(",","").toDouble()
                var nomePrestador = binding.txtNomePrestador.text.toString()

                var search = ServiceSearchResponse(id_servico,nomeServico,preco,tipo_preco,nomePrestador)

                listener.OnItemClick(search)
            }
        }

        fun bind(context: Context, item : ServiceSearchResponse){
            id_servico = item.id
            binding.txtNomePrestador.text = item.nomePrestador
            binding.txtNomeServico.text = item.nomeServico
            binding.txtPrecoServico.text = binding.inputCurrency.formatCurrency((item.preco*100).toLong()).toString()
            when(item.tipoPreco){
                1 -> {
                    binding.txtTipoServico.text = Constantes.Hora()
                    tipo_preco = item.tipoPreco
                }
                2 -> {
                    binding.txtTipoServico.text = Constantes.Diaria()
                    tipo_preco = item.tipoPreco
                }
                3 -> {
                    binding.txtTipoServico.text = Constantes.Fechado()
                    tipo_preco = item.tipoPreco
                }
            }
        }

    }

    interface OnItemClickListener{
        fun OnItemClick(serviceSearchResponse: ServiceSearchResponse)
    }
}