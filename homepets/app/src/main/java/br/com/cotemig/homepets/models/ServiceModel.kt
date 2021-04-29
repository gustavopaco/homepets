package br.com.cotemig.homepets.models

data class ServiceModel(
    var email : String,
    var nomeServico : String,
    var preco : Double,
    var tipoPreco : Int
)