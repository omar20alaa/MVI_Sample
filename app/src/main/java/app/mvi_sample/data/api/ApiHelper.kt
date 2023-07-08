package app.mvi_sample.data.api

import app.mvi_sample.data.model.User

interface ApiHelper {

    suspend fun getUsers() : List<User>


}