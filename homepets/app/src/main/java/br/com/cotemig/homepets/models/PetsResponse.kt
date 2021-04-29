package br.com.cotemig.homepets.models

import java.io.Serializable

data class PetsResponse(
    var id : Int = 0,
    var nome : String = "",
    var raca : String = "",
    var sexo : String = "",
    var tipoPet : Int = 0
) : Serializable