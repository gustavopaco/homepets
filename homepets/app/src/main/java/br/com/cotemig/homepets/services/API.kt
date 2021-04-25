package br.com.cotemig.homepets.services

import br.com.cotemig.homepets.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface API {

    @POST("register")
    fun createUser(
        @Body userAPI: UserAPI
    ) :Call<RegisterResponse>

    @POST("login")
    fun getAuth(
        @Body userAPI: UserAPI
    ) : Call<LoginResponse>

}