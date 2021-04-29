package br.com.cotemig.homepets.models

data class ServiceModel(
    var email : String = "",
    var id : Int = 0,
    var nomeServico : String = "",
    var preco : Double = 0.00,
    var tipoPreco : Int = 0
)