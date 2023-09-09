package presentation.base

sealed interface ErrorState {
    data object NoInternet : ErrorState
    data object HasNoPermission : ErrorState
    data object UnAuthorized : ErrorState
    data object ServerError : ErrorState
    data class UnknownError(val errorMessage: String) : ErrorState
    data object NotFound : ErrorState
}
