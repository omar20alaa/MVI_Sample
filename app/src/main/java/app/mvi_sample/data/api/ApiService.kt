package app.mvi_sample.data.api

import app.mvi_sample.data.model.User
import retrofit2.http.GET

interface ApiService {


    @GET("users")
    suspend fun getUsers(): List<User>


}