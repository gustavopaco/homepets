package br.com.cotemig.homepets.models

data class PetModel(
    var email : String,
    var nome : String,
    var raca : String,
    var sexo : String,
    var tipoPet : Int
)