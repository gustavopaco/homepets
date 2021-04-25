package br.com.cotemig.homepets.models

data class UserAPI(
    var nome : String = "",
    var email : String = "",
    var senha: String = "",
    var stats: Int = 0
)