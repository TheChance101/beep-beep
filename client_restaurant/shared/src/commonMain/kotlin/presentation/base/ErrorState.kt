package presentation.base

sealed interface ErrorState {
    object NoInternet : ErrorState
    object HasNoPermission : ErrorState
    object RequestFailed : ErrorState
    object UnAuthorized : ErrorState
    data class UserNotExist(val errorMessage: String) : ErrorState
    data class WrongPassword(val errorMessage: String) : ErrorState

    data class InvalidCredentials(val errorMessage: String) : ErrorState
    data class UnknownError(val errorMessage: String) : ErrorState
}
