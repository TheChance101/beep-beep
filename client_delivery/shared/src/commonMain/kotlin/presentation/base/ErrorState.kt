package presentation.base

sealed interface ErrorState {
    object NoInternet : ErrorState
    object UnAuthorized : ErrorState
    object RequestFailed : ErrorState
    object InvalidUsername : ErrorState
    object InvalidPassword : ErrorState
    data object UserNotFound : ErrorState
    data class WrongPassword(val message: String) : ErrorState
}
