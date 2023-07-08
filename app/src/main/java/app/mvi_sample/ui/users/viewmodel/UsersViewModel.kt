package app.mvi_sample.ui.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mvi_sample.data.repository.UsersRepository
import app.mvi_sample.ui.users.intent.UserIntent
import app.mvi_sample.ui.users.viewstate.UsersState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception


@ExperimentalCoroutinesApi
class UsersViewModel(
    private val repository: UsersRepository
) : ViewModel() {

    val userIntent = Channel<UserIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<UsersState>(UsersState.Idle)
    val state: StateFlow<UsersState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is UserIntent.FetchUsers -> fetchUser()
                }
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            _state.value = UsersState.Loading
            _state.value = try {
                UsersState.Users(repository.getUsers())
            } catch (e: Exception) {
                UsersState.Error(e.localizedMessage)
            }
        }
    }


}