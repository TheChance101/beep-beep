package presentation.base

sealed interface ErrorState {
    object WifiDisabled : ErrorState
    object NoInternet : ErrorState
    object NetworkNotSupported : ErrorState
    object UnAuthorized : ErrorState
    object RequestFailed : ErrorState
    object HasNoPermission : ErrorState
    object InvalidCredentials : ErrorState
    data class InvalidUserName(val errorMessage: String) : ErrorState
    data class InvalidPassword(val errorMessage: String) : ErrorState
}
