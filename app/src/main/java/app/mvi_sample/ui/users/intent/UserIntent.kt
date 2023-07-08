package app.mvi_sample.ui.users.intent

sealed class UserIntent {


    object FetchUsers : UserIntent()

}
