package app.mvi_sample.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.mvi_sample.data.api.ApiHelper
import app.mvi_sample.data.repository.UsersRepository
import app.mvi_sample.ui.users.viewmodel.UsersViewModel

class ViewModelFactory(
    private val apiHelper: ApiHelper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            return UsersViewModel(UsersRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}