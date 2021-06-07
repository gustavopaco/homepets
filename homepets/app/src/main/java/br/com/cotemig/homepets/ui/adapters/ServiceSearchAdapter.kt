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
        private var nomePrestador = binding.txtNomePrestador
        private var nomeServico = binding.txtNomeServico
        private var precoServico = binding.txtPrecoServico
        private var tipoPreco = binding.txtTipoServico
        private var currency = binding.inputCurrency
        private var id_servico = -1
        private var tipo_preco = -1

        override fun onClick(v: View?) {
           val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                var nomeS = nomeServico.text.toString()
                var preco = precoServico.text.toString().replace("$","").replace(",","").toDouble()
                var nomeP = nomePrestador.text.toString()

                var search = ServiceSearchResponse(id_servico,nomeS,preco,tipo_preco,nomeP)

                listener.OnItemClick(search)
            }
        }

        fun bind(context: Context, item : ServiceSearchResponse){
            id_servico = item.id
            nomePrestador.text = item.nomePrestador
            nomeServico.text = item.nomeServico
            precoServico.text = currency.formatCurrency((item.preco*100).toLong()).toString()
            when(item.tipoPreco){
                1 -> {
                    tipoPreco.text = Constantes.Hora()
                    tipo_preco = item.tipoPreco
                }
                2 -> {
                    tipoPreco.text = Constantes.Diaria()
                    tipo_preco = item.tipoPreco
                }
                3 -> {
                    tipoPreco.text = Constantes.Fechado()
                    tipo_preco = item.tipoPreco
                }
            }
        }

    }

    interface OnItemClickListener{
        fun OnItemClick(serviceSearchResponse: ServiceSearchResponse)
    }
}