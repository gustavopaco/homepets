import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.cotemig.homepets.databinding.ItemSpinnerBinding
import br.com.cotemig.homepets.models.PetsResponse

class PetsSpinnerAdapter(var context: Context, var list: List<PetsResponse>?) : BaseAdapter() {
    private lateinit var binding: ItemSpinnerBinding
    override fun getCount(): Int {
        return list!!.size
    }

    override fun getItem(position: Int): Any {
        return ""
    }

    override fun getItemId(position: Int): Long {
        return 0
    }


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        binding = ItemSpinnerBinding.inflate(LayoutInflater.from(context),parent,false)

        binding.nomeCachorro.text = list!![position].nome

        return binding.root
    }

}