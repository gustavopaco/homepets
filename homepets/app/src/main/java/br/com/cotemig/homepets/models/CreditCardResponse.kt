package br.com.cotemig.homepets.models

import java.io.Serializable

data class CreditCardResponse(
    var id: Int,
    var numero: String,
    var nomeTitular: String,
    var validadeMesAno: String,
    var codigoValidacao: String
) : Serializable