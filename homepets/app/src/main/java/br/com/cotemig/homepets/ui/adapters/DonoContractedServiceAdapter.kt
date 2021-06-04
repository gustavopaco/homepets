package br.com.cotemig.homepets.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.homepets.databinding.ItemDonoHistoricoServicosBinding
import br.com.cotemig.homepets.models.DonoHFContractedServiceResponse
import br.com.cotemig.homepets.util.Constantes

class DonoContractedServiceAdapter(var context: Context, var list: List<DonoHFContractedServiceResponse>?) : RecyclerView.Adapter<DonoContractedServiceAdapter.ViewHFDonoContractedService>() {
    private lateinit var binding: ItemDonoHistoricoServicosBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHFDonoContractedService {
        binding = ItemDonoHistoricoServicosBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHFDonoContractedService(binding)
    }

    override fun onBindViewHolder(holder: ViewHFDonoContractedService, position: Int) {
        holder.bind(context,list!![position])
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class ViewHFDonoContractedService(binding: ItemDonoHistoricoServicosBinding) : RecyclerView.ViewHolder(binding.root){

        private var nomeDonoPrestador = binding.txtNomePrestador
        private var nomeServico = binding.txtNomeServico
        private var preco = binding.txtPrecoServico
        private var tipoPreco = binding.txtTipoServico
        private var dataExecucao = binding.txtDataServico
        private var nomePet = binding.txtNomePet
        private var currency = binding.inputCurrency

        fun bind(context: Context, item : DonoHFContractedServiceResponse){
            nomeDonoPrestador.text = item.nomePrestador
            nomeServico.text = item.nomeServico
            preco.text = currency.formatCurrency((item.preco*100).toLong()).toString()
            when(item.tipoPreco){
                1 -> tipoPreco.text = Constantes.Hora()
                2 -> tipoPreco.text = Constantes.Diaria()
                3 -> tipoPreco.text = Constantes.Fechado()
            }
            dataExecucao.text = item.dataExecucao
            nomePet.text = item.nomePet
        }
    }
}