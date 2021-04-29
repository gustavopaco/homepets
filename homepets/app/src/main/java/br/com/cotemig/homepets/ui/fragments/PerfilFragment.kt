package br.com.cotemig.homepets.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.FragmentPerfilBinding
import br.com.cotemig.homepets.models.RegisterModel
import br.com.cotemig.homepets.models.TokenModelResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.ui.activities.HomeActivity
import br.com.cotemig.homepets.ui.activities.LoginUserActivity
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(layoutInflater,container,false)


        var activity = context as HomeActivity
        var nome = SharedPreferenceHelper.readString(activity,"userpreferences","nome","")
        var email = SharedPreferenceHelper.readString(activity,"userpreferences","email","")
        binding.inputNomeUsuario.setText(nome)
        binding.inputEmailUsuario.setText(email)


        binding.btnSalvarPerfil.setOnClickListener {

           if(validaCampos()){

               /* RETROFIT AQUI*/
               var nome = binding.inputNomeUsuario.text.toString()
               var senha = binding.inputNovaSenha1.text.toString()
               var stats = SharedPreferenceHelper.readInt(activity,"userpreferences","stats",-1)

               var registerModel = RegisterModel(nome,email.toString(),senha,stats)

               RetrofitInitializer().serviceAPI().updateUser(registerModel).enqueue(object : Callback<TokenModelResponse>{
                   override fun onResponse(
                       call: Call<TokenModelResponse>,
                       response: Response<TokenModelResponse>
                   ) {
                       response?.let {
                           if(it.code() == 200){
                               MaterialDialog.Builder(activity).title("Sucesso").content("Dados alterados! Por favor, logue novamente.").positiveText("Ok").show()
                               SharedPreferenceHelper.saveString(activity,"userpreferences","email","")
                               SharedPreferenceHelper.saveString(activity,"userpreferences","senha","")
                               SharedPreferenceHelper.saveString(activity,"userpreferences","token","")
                               SharedPreferenceHelper.saveInt(activity,"userpreferences","stats",-1)
                               goLoginActivity()
                           }else{
                               MaterialDialog.Builder(activity).title("Erro").content(it.errorBody()!!.string()).positiveText("Ok").show()
                           }
                       }
                   }

                   override fun onFailure(call: Call<TokenModelResponse>, t: Throwable) {
                       MaterialDialog.Builder(activity).title("Erro").content("API FORA DO AR").positiveText("Ok").show()
                   }

               })
           }
        }

        return binding.root
    }

    private fun validaCampos() : Boolean{

        var activity = context as HomeActivity
        var validacao = true
        var senhaAtual = SharedPreferenceHelper.readString(activity,"userpreferences","senha","")

        if(binding.inputNomeUsuario.text.toString().isEmpty()){
            binding.inputNomeUsuario.setError("Digite seu nome")
            validacao = false
        }

        if(binding.inputSenhaAtual.text.toString().isEmpty()){
            binding.inputSenhaAtual.setError("Informe sua Senha Atual")
            validacao = false
        }else if(!binding.inputSenhaAtual.text.toString().equals(senhaAtual,false)){
            binding.inputSenhaAtual.setError("Senha Atual errada")
            validacao = false
        }

        if(binding.inputNovaSenha1.text.toString().isEmpty()){
            binding.inputNovaSenha1.setError("Informe sua Nova Senha")
            validacao = false
        }else if(binding.inputNovaSenha1.text.toString().length < 5){
            binding.inputNovaSenha1.setError("Min 5 Caracteres")
            validacao = false
        }

        if(binding.inputNovaSenha2.text.toString().isEmpty()){
            binding.inputNovaSenha2.setError("Informe sua Nova Senha")
            validacao = false
        }else if(binding.inputNovaSenha2.text.toString().length < 5){
            binding.inputNovaSenha2.setError("Min 5 Caracteres")
            validacao = false
        }
        if(binding.inputNovaSenha1.text.toString().length >= 5 && binding.inputNovaSenha2.text.toString().length >= 5){
            if(!binding.inputNovaSenha1.text.toString().equals(binding.inputNovaSenha2.text.toString(),false)){
                MaterialDialog.Builder(activity).title("Erro").content("Nova senha e Confirmacao devem ser a mesma").positiveText("Ok").show()
                validacao = false
            }
        }

        return validacao


    }

    private fun goLoginActivity(){
        var activity = context as HomeActivity
        startActivity(Intent(activity,LoginUserActivity::class.java))
    }

}