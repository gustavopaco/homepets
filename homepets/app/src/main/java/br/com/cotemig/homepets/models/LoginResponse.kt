package br.com.cotemig.homepets.models

data class LoginResponse(
    var token: String,
    var stats: Int,
    var userAPI: UserAPI
)