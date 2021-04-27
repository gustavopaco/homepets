package br.com.cotemig.homepets.models

data class RegisterModel(
    var nome : String = "",
    var email : String = "",
    var senha: String = "",
    var stats: Int = 0
)