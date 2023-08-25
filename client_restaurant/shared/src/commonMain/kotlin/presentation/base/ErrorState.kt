package presentation.base

sealed interface ErrorState {
    object WifiDisabled : ErrorState
    object NoInternet : ErrorState
    object NetworkNotSupported : ErrorState
    object UnAuthorized : ErrorState
    object RequestFailed : ErrorState
    object HasNoPermission : ErrorState
    class InvalidCredentials(val errorMessage: String) : ErrorState

    class UserNotExist(val errorMessage: String) : ErrorState
}
