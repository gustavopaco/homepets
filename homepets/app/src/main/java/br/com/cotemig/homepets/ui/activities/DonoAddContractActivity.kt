package br.com.cotemig.homepets.ui.activities

import PetsSpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.isVisible
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityDonoAddContractBinding
import br.com.cotemig.homepets.models.ContractService
import br.com.cotemig.homepets.models.PetsResponse
import br.com.cotemig.homepets.models.ServiceSearchResponse
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DonoAddContractActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonoAddContractBinding
    private lateinit var petClickado: PetsResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonoAddContractBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPets()

        binding.btnVoltar.setOnClickListener {
            finish()
        }

        binding.btnSelecionarData.setOnClickListener {
            getDataExecucao()
        }

        binding.btnAlterarData.setOnClickListener {
            getDataExecucao()
        }

        binding.btnContratar.setOnClickListener {
            if(validaData()){
                createContract()
            }
        }
    }

    private fun createContract() {
        var token = SharedPreferenceHelper.readString(this, "userpreferences", "token", "")
        var serviceSearchResponse = intent.getSerializableExtra("contrato") as ServiceSearchResponse
        var dataExecucao = binding.txtDataExecucao.text.toString()
        var contractService = ContractService(serviceSearchResponse.id,dataExecucao,petClickado.id)

        RetrofitInitializer().serviceAPI().contractService(token = "Bearer $token",contractService).enqueue(object  : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                response?.let {
                    if(it.code() == 200){
                        MaterialDialog(this@DonoAddContractActivity).show {
                            title(R.string.sucesso)
                            message(null, "Contrato realizado com sucesso")
                            positiveButton(R.string.ok){
                                finish() // MATANDO ACTIVITY DE CADASTRO APOS CRIAR CONTRATO E VOLTANDO PARA TELA HOMEACTIVITY
                            }
                        }
                    }else{
                        MaterialDialog(this@DonoAddContractActivity).show {
                            title(R.string.erro)
                            message(null,it.errorBody()!!.string())
                            positiveButton(null,"Ok")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                MaterialDialog(this@DonoAddContractActivity).show {
                    title(R.string.erro)
                    message(null,"API FORA DO AR")
                    positiveButton(null,"Ok")
                }
            }

        })
    }

    private fun getDataExecucao(): Boolean {
        MaterialDialog(this).show {
            datePicker(minDate = Calendar.getInstance()) { dialog, datetime ->
                var dataSelecionada = "${datetime.dayOfMonth}/${datetime.month}/${datetime.year}"
                binding.txtDataExecucao.text = dataSelecionada
                if (binding.btnSelecionarData.isVisible) {
                    binding.btnSelecionarData.isVisible = false;
                    binding.txtDataExecucao.isVisible = true
                    binding.btnAlterarData.isVisible = true
                }
            }
        }
        return true
    }

    private fun getPets() {
        var token = SharedPreferenceHelper.readString(this, "userpreferences", "token", "")
        RetrofitInitializer().serviceAPI().getPets(token = "Bearer $token")
            .enqueue(object : Callback<List<PetsResponse>> {
                override fun onResponse(call: Call<List<PetsResponse>>, response: Response<List<PetsResponse>>) {
                    response?.let {
                        if (it.code() == 200) {
                            var petListaTeste = PetsSpinnerAdapter(this@DonoAddContractActivity, it.body())
                            binding.spinner.adapter = petListaTeste

                            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    petClickado = it.body()!![position]
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    TODO("Not yet implemented")
                                }
                            }
                        } else {
                            MaterialDialog(this@DonoAddContractActivity).show {
                                title(R.string.erro)
                                message(null, it.errorBody()!!.string())
                                positiveButton(null, "Ok")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<PetsResponse>>, t: Throwable) {
                    MaterialDialog(this@DonoAddContractActivity).show {
                        title(R.string.erro)
                        message(null, "API FORA DO AR")
                        positiveButton(null, "Ok")
                    }
                }
            })
    }

    private fun validaData() : Boolean{
        if(binding.txtDataExecucao.text.toString().isEmpty()){
            MaterialDialog(this@DonoAddContractActivity).show {
                title(R.string.erro)
                message(null, "Selecione uma data para o serviço")
                positiveButton(null, "Ok")
            }
            return false
        }
        return true
    }
}