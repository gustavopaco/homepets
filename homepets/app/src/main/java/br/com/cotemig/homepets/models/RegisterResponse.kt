package br.com.cotemig.homepets.models

data class RegisterResponse(
    var token : String,
    var userAPI: UserAPI
)
