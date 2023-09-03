package presentation.base

sealed interface ErrorState {
    data object NoInternet : ErrorState
    data object HasNoPermission : ErrorState
    data object RequestFailed : ErrorState
    data object UnAuthorized : ErrorState
    data class UserNotExist(val errorMessage: String) : ErrorState
    data class InvalidCredentials(val errorMessage: String) : ErrorState
    data class UnknownError(val errorMessage: String) : ErrorState

    data object NotFound : ErrorState
}
