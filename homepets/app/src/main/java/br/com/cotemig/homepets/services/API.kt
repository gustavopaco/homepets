package br.com.cotemig.homepets.services

import br.com.cotemig.homepets.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {

    @POST("register")
    fun createUser(
        @Body registerModel: RegisterModel
    ) :Call<TokenModelResponse>

    @POST("login")
    fun getAuth(
        @Body authModel: AuthModel
    ) : Call<TokenModelResponse>

    @GET("URLAQUI")
    fun getPets() : Call<List<PetModelResponse>>
}