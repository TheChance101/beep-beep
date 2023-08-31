package presentation.base

sealed interface ErrorState {
    object NoInternet : ErrorState
    object HasNoPermission : ErrorState
    object RequestFailed : ErrorState
    object UnAuthorized : ErrorState
    data class UserNotExist(val errorMessage: String) : ErrorState
    data class InvalidCredentials(val errorMessage: String) : ErrorState
    object UnknownError : ErrorState

    object NotFound : ErrorState
}
