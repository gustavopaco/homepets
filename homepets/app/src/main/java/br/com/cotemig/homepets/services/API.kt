package br.com.cotemig.homepets.services

import br.com.cotemig.homepets.models.*
import retrofit2.Call
import retrofit2.http.*

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
        @Body updateUserModel: UpdateUserModel
    ) : Call<TokenModelResponse>

    @POST("URL_ADD_PET_AQUI")
    fun createPet(
        @Body petModel: PetModel
    ) : Call<TokenModelResponse>

    @POST("URL_ADD_SERVICO_AQUI")
    fun createService(
        @Body serviceModel: ServiceModel
    ) : Call<TokenModelResponse>

    @GET("URL_LISTA_PETS_AQUI/{email}")
    fun getPets(
        @Path ("email") email : String
    ) : Call<List<PetsResponse>>

    @GET("URL_LISTA_SERVICOS_AQUI/{email}")
    fun getServices(
        @Path("email") email : String
    ) : Call<List<ServicesResponse>>

    @POST("URL_UPDATE_DADOS_SERVICO_AQUI")
    fun updateService(
        @Body serviceModel: ServiceModel
    ) : Call<TokenModelResponse>

    @DELETE("URL_DELETE_SERVICO_AQUI/{email}/{id}")
    fun deleteService(
        @Path("email") email : String,
        @Path("id") id : Int

    ) : Call<Void>

    @DELETE("URL_DELETE_PET_AQUI/{email}/{id}")
    fun deletePet(
        @Path("email") email: String,
        @Path("id") id: Int
    ) : Call<Void>

}