package br.com.cotemig.homepets.services

import br.com.cotemig.homepets.models.*
import retrofit2.Call
import retrofit2.http.*

interface API {

    @Headers("Content-Type: application/json")
    @POST("login/register")
    fun createUser(
        @Body registerModel: RegisterModel
    ) :Call<TokenModelResponse>

    @Headers("Content-Type: application/json")
    @POST("login/login")
    fun getAuth(
        @Body authModel: AuthModel
    ) : Call<TokenModelResponse>

    @Headers("Content-Type: application/json")
    @POST("user")
    fun updateUser(
        @Header("Authorization") token: String,
        @Body updateUserModel: UpdateUserModel
    ) : Call<Void>

    @Headers("Content-Type: application/json")
    @POST("pet")
    fun createPet(
        @Header("Authorization") token: String,
        @Body petModel: PetModel
    ) : Call<Void>

    @Headers("Content-Type: application/json")
    @POST("service")
    fun createService(
        @Header("Authorization") token: String,
        @Body serviceModel: ServiceModel
    ) : Call<Void>

    @Headers("Content-Type: application/json")
    @GET("pet")
    fun getPets(
        @Header("Authorization") token: String
    ) : Call<List<PetsResponse>>

    @Headers("Content-Type: application/json")
    @GET("service")
    fun getServices(
        @Header("Authorization") token: String
    ) : Call<List<ServicesResponse>>

    @Headers("Content-Type: application/json")
    @DELETE("service/{id}")
    fun deleteService(
        @Header("Authorization") token: String,
        @Path("id") id : Int
    ) : Call<Void>

    @Headers("Content-Type: application/json")
    @DELETE("pet/{id}")
    fun deletePet(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Call<Void>

}