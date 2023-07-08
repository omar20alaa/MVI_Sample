package app.mvi_sample.ui.users.viewstate

import app.mvi_sample.data.model.User

sealed class UsersState {


    object Idle : UsersState()
    object Loading : UsersState()
    data class Users(val users : List<User>) : UsersState()
    data class Error(val error : String?) : UsersState()

}