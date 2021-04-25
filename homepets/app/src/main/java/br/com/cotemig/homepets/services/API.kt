package br.com.cotemig.homepets.services

import br.com.cotemig.homepets.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface API {

    @FormUrlEncoded
    @POST("SETAR URL AQUI")
    fun createUser(
        @Field("nome") nome: String,
        @Field("email") email: String,
        @Field("senha") senha: String,
        @Field("stats") stats: Int
    ) :Call<DefaultResponse>

}