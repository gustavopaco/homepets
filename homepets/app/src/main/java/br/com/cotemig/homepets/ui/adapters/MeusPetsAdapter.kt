package br.com.cotemig.homepets.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.homepets.databinding.ItemDonoMeusPetsBinding
import br.com.cotemig.homepets.models.PetsResponse

class MeusPetsAdapter (var context : Context, var list: List<PetsResponse>?, val listener : OnItemClickListener) : RecyclerView.Adapter<MeusPetsAdapter.ViewMeusPets>(){

    private lateinit var binding: ItemDonoMeusPetsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewMeusPets {
        binding = ItemDonoMeusPetsBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewMeusPets(binding)
    }

    override fun onBindViewHolder(holder: ViewMeusPets, position: Int) {
        holder.bind(context,list!![position])
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    inner class ViewMeusPets(binding : ItemDonoMeusPetsBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        private var nome_pet = binding.txtnomePet
        private var raca_pet = binding.txtRacaPet
        private var sexo_pet = binding.txtsexoPet
        private var tipo_pet = binding.txtTipoPet
        private var tipopetID = -1
        private var id = -1

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                var nomepet = nome_pet.text.toString()
                var racapet = raca_pet.text.toString()
                var sexopet = sexo_pet.text.toString()

                var pet = PetsResponse(id,nomepet,racapet,sexopet,tipopetID,)
                listener.OnItemClick(position,pet)
            }
        }

        fun bind(context: Context, item : PetsResponse){
            id = item.id
            nome_pet.text = item.nome
            raca_pet.text = item.raca
            sexo_pet.text = item.sexo
            if(item.tipoPet == 1){
                tipo_pet.text = "Cachorro"
                tipopetID = 1
            }else{
                tipo_pet.text = "Gato"
                tipopetID = 2
            }

        }

    }
    interface OnItemClickListener{
        fun OnItemClick(position: Int, pet: PetsResponse)
    }

}