package app.mvi_sample.data.repository

import app.mvi_sample.data.api.ApiHelper

class UsersRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}