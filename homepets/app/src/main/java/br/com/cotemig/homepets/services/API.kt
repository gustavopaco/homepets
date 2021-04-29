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

    @POST("URL_UPDATE_PERFIL_USUARIO_AQUI")
    fun updateUser(
        @Body registerModel: RegisterModel
    ) : Call<TokenModelResponse>

    @GET("URL_LISTA_PETS_AQUI")
    fun getPets() : Call<List<PetModel>>

    @POST("URL_ADD_PET_AQUI")
    fun createPet(
        @Body petModel: PetModel
    ) : Call<TokenModelResponse>

    @POST("URL_ADD_SERVICO_AQUI")
    fun createService(
        @Body serviceModel: ServiceModel
    ) : Call<TokenModelResponse>
}