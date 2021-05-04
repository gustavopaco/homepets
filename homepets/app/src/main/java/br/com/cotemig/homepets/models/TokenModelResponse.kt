package br.com.cotemig.homepets.models

data class TokenModelResponse(
    var token: String,
    var nome: String,
    var stats: Int
)