package br.com.cotemig.homepets.models

import java.io.Serializable

data class ServiceSearchResponse(
    var id : Int,
    var nomeServico : String,
    var preco : Double,
    var tipoPreco : Int,
    var nomePrestador : String
) : Serializable