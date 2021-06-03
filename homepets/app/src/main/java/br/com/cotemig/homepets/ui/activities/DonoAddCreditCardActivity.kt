package br.com.cotemig.homepets.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.databinding.ActivityDonoAddCreditCardBinding
import br.com.cotemig.homepets.models.CreditCardModel
import br.com.cotemig.homepets.services.RetrofitInitializer
import br.com.cotemig.homepets.util.SharedPreferenceHelper
import com.afollestad.materialdialogs.MaterialDialog
import com.maxpilotto.creditcardview.models.CardArea
import com.maxpilotto.creditcardview.models.CardInput
import com.maxpilotto.creditcardview.util.NumberFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonoAddCreditCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonoAddCreditCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonoAddCreditCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.btnCadastrarCartao.setOnClickListener {
            if (validaCampos()) {
                createCreditCard()
            }
        }

        binding.card.setAreaClickListener { card, area ->
            if (area == CardArea.LEFT) {
               card.flipOnClick = true
            } else if (area == CardArea.RIGHT) {
                card.flipOnClick = true
            }
        }

        binding.card.apply {
            pairInput(CardInput.NUMBER,binding.txtNumeroCartao)
            pairInput(CardInput.HOLDER,binding.txtNomeTitular)
            pairInput(CardInput.EXPIRY,binding.txtValidade)
            pairInput(CardInput.CVV,binding.txtCodValidacao)
        }

        binding.card.numberFormat = NumberFormat("%d4 %d4 %d4 %d4",false)

        binding.txtNumeroCartao.setOnFocusChangeListener { v, hasFocus ->
            binding.card.flipOnEdit = true
            binding.txtNumeroCartao.error = null
        }
        binding.txtNomeTitular.setOnFocusChangeListener { v, hasFocus ->
            binding.card.flipOnEdit = true
            binding.txtNomeTitular.error = null
        }
        binding.txtValidade.setOnFocusChangeListener { v, hasFocus ->
            binding.card.flipOnEdit = true
            binding.txtValidade.error = null
        }
        binding.txtCodValidacao.setOnFocusChangeListener { v, hasFocus ->
            binding.card.flipOnEdit = true
            binding.txtCodValidacao.error = null
        }


    }

    private fun createCreditCard() {
        var token = SharedPreferenceHelper.readString(this,"userpreferences","token","")
        var numeroCartao = binding.txtNumeroCartao.text.toString()
        var nomeTitular = binding.txtNomeTitular.text.toString()
        var validadeMesAno = binding.txtValidade.text.toString()
        var cvv = binding.txtCodValidacao.text.toString()

        var creditCard = CreditCardModel(numeroCartao,nomeTitular,validadeMesAno,cvv)

        RetrofitInitializer().serviceAPI().createCreditCard(token= "Bearer $token",creditCard).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                response?.let {
                    if(it.code() == 200){
                        MaterialDialog(this@DonoAddCreditCardActivity).show {
                            title(R.string.sucesso)
                            message(null, "Cartão de Crédito cadastrado com sucesso.")
                            positiveButton(R.string.ok){
                                finish() // MATANDO ACTIVITY DE CADASTRO APOS CRIAR CONTRATO E VOLTANDO PARA TELA LISTA DE CARTAO
                            }
                        }
                    }else{
                        MaterialDialog(this@DonoAddCreditCardActivity).show {
                            title(R.string.erro)
                            message(null,it.errorBody()!!.string())
                            positiveButton(null,"Ok")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                MaterialDialog(this@DonoAddCreditCardActivity).show {
                    title(R.string.erro)
                    message(null,"API FORA DO AR")
                    positiveButton(null,"Ok")
                }
            }
        })
    }

    private fun validaCampos(): Boolean {
        var check = true

        if(binding.txtNumeroCartao.text.toString().isEmpty()){
            binding.txtNumeroCartao.error = "Informe o Numero do cartao"
            check = false
        }
        if(binding.txtNomeTitular.text.toString().isEmpty()){
            binding.txtNomeTitular.error = "Informe o Titular"
            check = false
        }
        if(binding.txtValidade.text.toString().isEmpty()){
            binding.txtValidade.error = "Informe Data de Vencimento"
            check = false
        }
        if(binding.txtCodValidacao.text.toString().isEmpty()){
            binding.txtCodValidacao.error = "Informe o CVV atras do cartão"
            check = false
        }
        return check
    }
}
