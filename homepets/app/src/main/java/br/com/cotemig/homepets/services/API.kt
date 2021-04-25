package br.com.cotemig.homepets.services

import br.com.cotemig.homepets.models.Pessoa
import br.com.cotemig.homepets.models.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface API {

    @POST("register")
    fun createUser(
        @Body pessoa : Pessoa
    ) :Call<Pessoa>

    @POST("login")
    fun getAuth(
        @Body pessoa: Pessoa
    ) : Call<Pessoa>

}