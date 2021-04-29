package br.com.cotemig.homepets.models

data class PetsResponse(
    var id : Int,
    var nome : String,
    var raca : String,
    var sexo : String,
    var tipoPet : Int
)