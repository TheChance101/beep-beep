package presentation.base

sealed interface ErrorState {
    data object NoInternet : ErrorState
    data object HasNoPermission : ErrorState
    data object UnAuthorized : ErrorState
    data object ServerError : ErrorState
    data class InvalidCredentials(val errorMessage: String) : ErrorState
    data class UnknownError(val errorMessage: String) : ErrorState
    data class NotFound(val errorMessage: String) : ErrorState
    data class InvalidUserName(val errorMessage: String) : ErrorState
    data class InvalidPassword(val errorMessage: String) : ErrorState
    data class InvalidDriverName(val errorMessage: String) : ErrorState
    data class InvalidDriverEmail(val errorMessage: String) : ErrorState
}
