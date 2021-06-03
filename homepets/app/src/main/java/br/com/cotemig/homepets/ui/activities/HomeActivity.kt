package br.com.cotemig.homepets.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityHomeBinding
import br.com.cotemig.homepets.ui.fragments.*
import br.com.cotemig.homepets.util.SharedPreferenceHelper

class HomeActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var stats = SharedPreferenceHelper.readInt(this@HomeActivity,"userpreferences","stats",-1)

        if(stats == 1){
            binding.headDonoPets.visibility = View.VISIBLE
        }else if(stats == 2){
            binding.headHotel.visibility = View.VISIBLE
            binding.btnPerfil.background = ContextCompat.getDrawable(this@HomeActivity,R.drawable.shape_hotel_perfil)
        }else{
            binding.headFreelancer.visibility = View.VISIBLE
            binding.btnPerfil.background = ContextCompat.getDrawable(this@HomeActivity,R.drawable.shape_freelancer_perfil)
        }

        setFragment(HomeFragment(),"home")

        binding.btnHome.setOnClickListener {
            menu(0)
        }
        binding.btnPerfil.setOnClickListener {
            getMenu(it,stats)
        }

        /* BOTOES DA TELA DE DONO DE PET */
        binding.btnDonoMeusPets.setOnClickListener {
            menu(2)
        }
        binding.btnDonoBuscaServicos.setOnClickListener {
            menu(3)
        }

        /* BOTOES DA TELA DONO DE HOTEL */
        binding.btnHotelMeusServicos.setOnClickListener {
            menu(4)
        }
        binding.btnHotelReservas.setOnClickListener {
            menu(5)
        }

        /* BOTOES DA TELA FREELANCER */
        binding.btnFreelancerMeusServicos.setOnClickListener {
            menu(6)
        }
        binding.btnFreelancerContratados.setOnClickListener {
            menu(7)
        }

    }

    fun setFragment(f: Fragment, name: String){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(binding.contenthome.id,f,name)
        ft.commit()
    }

    private fun menu(menu : Int){

        when(menu){
            0 -> setFragment(HomeFragment(),"home")
            1 -> setFragment(PerfilFragment(),"perfil")
            2 -> setFragment(DonoMeusPetsFragment(),"donomeuspets")
            3 -> setFragment(DonoBuscaServicosFragment(),"donoservicos")
            4 -> setFragment(HotelFreelancerServicosFragment(),"hotelservicos")
            5 -> setFragment(HotelReservasFragment(),"hotelreservas")
            6 -> setFragment(HotelFreelancerServicosFragment(),"freelancerservicos")
            7 -> setFragment(FreelancerContratadosFragment(),"freelancercontratados")
        }

    }

    private fun getMenu(view: View, stats : Int){
        val popupMenu = PopupMenu(this,view)
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.submenu1 -> {
                    menu(1)
                    true
                }
                R.id.submenu2 -> {
                    startActivity(Intent(this,DonoCreditCardActivity::class.java))
                    true
                }
                R.id.submenu3 -> {
                    SharedPreferenceHelper.saveString(this@HomeActivity,"userpreferences","nome","")
                    SharedPreferenceHelper.saveString(this@HomeActivity,"userpreferences","email","")
                    SharedPreferenceHelper.saveString(this@HomeActivity,"userpreferences","senha","")
                    SharedPreferenceHelper.saveString(this@HomeActivity,"userpreferences","token","")
                    SharedPreferenceHelper.saveInt(this@HomeActivity,"userpreferences","stats",-1)
                    startActivity(Intent(this,LoginUserActivity::class.java))
                    finish()
                    true
                }
                else ->  false
            }
        }
        popupMenu.inflate(R.menu.menu_layoutfile)
        if(stats != 1){
            popupMenu.menu.findItem(R.id.submenu2).isVisible = false
        }
        popupMenu.show()
    }
}