package br.com.cotemig.homepets.models

data class CreditCardModel(
    var numero: String,
    var nomeTitular: String,
    var validadeMesAno: String,
    var codigoValidacao: String
)