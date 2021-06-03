package br.com.cotemig.homepets.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.homepets.databinding.ItemDonoCreditCardBinding
import br.com.cotemig.homepets.models.CreditCardModel
import br.com.cotemig.homepets.models.CreditCardResponse
import com.maxpilotto.creditcardview.models.CardInput
import com.maxpilotto.creditcardview.util.NumberFormat

class CreditCardAdapter(var context: Context, var list: List<CreditCardResponse>?, val listener: OnItemClickListener) : RecyclerView.Adapter<CreditCardAdapter.ViewCreditCard>() {
    private lateinit var binding: ItemDonoCreditCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCreditCard {
        binding = ItemDonoCreditCardBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewCreditCard(binding)
    }

    override fun onBindViewHolder(holder: ViewCreditCard, position: Int) {
        holder.bind(context,list!![position])
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    inner class ViewCreditCard(binding: ItemDonoCreditCardBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        private var id_cartao = -1
        private var numeroCartao = binding.txtNumeroCartao
        private var titular = binding.txtNomeTitular
        private var validade = binding.txtValidade
        private var codvv = binding.txtCodValidacao
        private var cartaoL = binding.card


        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                var ncartao = numeroCartao.text.toString()
                var ntitular = titular.text.toString()
                var validadeMesAno = validade.text.toString()
                var codValidacao = codvv.text.toString()

                var cartaoCreditoResponse = CreditCardResponse(id_cartao,ncartao,ntitular,validadeMesAno,codValidacao)

                listener.OnItemClick(position,cartaoCreditoResponse)
            }
        }

        fun bind(context: Context, item: CreditCardResponse){
            id_cartao = item.id
            numeroCartao.text = item.numero
            titular.text = item.nomeTitular
            validade.text = item.validadeMesAno
            codvv.text = item.codigoValidacao

            cartaoL.number = item.numero
            cartaoL.holder = item.nomeTitular
            cartaoL.expiry = item.validadeMesAno
            cartaoL.cvv = item.codigoValidacao

            cartaoL.apply {
                pairInput(CardInput.NUMBER,numeroCartao)
                pairInput(CardInput.HOLDER,titular)
                pairInput(CardInput.EXPIRY,validade)
                pairInput(CardInput.CVV,codvv)
            }

            cartaoL.numberFormat = NumberFormat("%d4 %d4 %d4 %d4",false)
        }
    }

    interface OnItemClickListener{
        fun OnItemClick(position: Int,creditCardResponse: CreditCardResponse)
    }
}