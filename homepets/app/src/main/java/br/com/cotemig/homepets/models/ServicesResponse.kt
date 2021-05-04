package br.com.cotemig.homepets.models

import java.io.Serializable

data class ServicesResponse(
    var id : Int,
    var nomeServico : String,
    var preco : Double,
    var tipoPreco : Int
) : Serializable