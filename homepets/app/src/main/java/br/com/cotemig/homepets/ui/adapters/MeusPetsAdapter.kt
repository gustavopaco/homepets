package br.com.cotemig.homepets.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.homepets.databinding.ItemDonoMeusPetsBinding
import br.com.cotemig.homepets.models.PetModel
import br.com.cotemig.homepets.models.PetsResponse
import br.com.cotemig.homepets.util.Constantes

class MeusPetsAdapter (var context : Context, var list: List<PetsResponse>?) : RecyclerView.Adapter<MeusPetsAdapter.ViewMeusPets>(){

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

    class ViewMeusPets(binding : ItemDonoMeusPetsBinding) : RecyclerView.ViewHolder(binding.root){

        private var nome_pet = binding.txtnomePet
        private var raca_pet = binding.txtRacaPet
        private var sexo_pet = binding.txtsexoPet
        private var tipo_pet = binding.txtTipoPet

        fun bind(context: Context,item : PetsResponse){
            nome_pet.text = item.nome
            raca_pet.text = item.raca
            sexo_pet.text = item.sexo
            if(item.tipoPet == 1){
                tipo_pet.text = "Cachorro"
            }else{
                tipo_pet.text = "Gato"
            }

        }

    }

}