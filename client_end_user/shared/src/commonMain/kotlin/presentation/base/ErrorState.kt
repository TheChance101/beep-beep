package presentation.base

import domain.utils.AuthorizationException
import domain.utils.InternetException
import domain.utils.RestaurantException


sealed interface ErrorState {
    data class CartIsFull(val message: String) : ErrorState
    data class RestaurantClosed(val message: String) : ErrorState
    // region Authorization
    data object UnAuthorized : ErrorState
    data object InvalidUsername : ErrorState
    data object InvalidPassword : ErrorState
    data object InvalidFullName : ErrorState
    data object InvalidEmail : ErrorState
    data object InvalidPhone : ErrorState
    data object InvalidAddress : ErrorState
    data class UserAlreadyExists(val message: String) : ErrorState
    data class UserNotFound(val message: String) : ErrorState
    data class WrongPassword(val message: String) : ErrorState
    data object LocationPermissionDenied : ErrorState
    // endregion

    // region Internet
    data object WifiDisabled : ErrorState
    data object NoInternet : ErrorState
    data object NetworkNotSupported : ErrorState
    data object RequestFailed : ErrorState
    data object SocketClosed : ErrorState
    // endregion
}


fun handelInternetException(
    exception: InternetException,
    onError: (t: ErrorState) -> Unit,
) {
    when (exception) {
        is InternetException.NetworkNotSupportedException -> onError(ErrorState.NetworkNotSupported)
        is InternetException.NoInternetException -> onError(ErrorState.NoInternet)
        is InternetException.WifiDisabledException -> onError(ErrorState.WifiDisabled)
    }
}
fun handelRestaurantException(
    exception: RestaurantException,
    onError: (t: ErrorState) -> Unit,
) {
    when (exception) {
        is RestaurantException.CartIsFullException -> onError(ErrorState.CartIsFull(exception.message.toString()))
        is RestaurantException.RestaurantClosedException -> onError(ErrorState.RestaurantClosed(exception.message.toString()))
    }
}

fun handelAuthorizationException(
    exception: AuthorizationException,
    onError: (t: ErrorState) -> Unit,
) {
    when (exception) {
        is AuthorizationException.UnAuthorizedException -> onError(ErrorState.UnAuthorized)
        is AuthorizationException.InvalidUsernameException -> onError(ErrorState.InvalidUsername)
        is AuthorizationException.InvalidPasswordException -> onError(ErrorState.InvalidPassword)
        is AuthorizationException.InvalidCredentialsException -> onError(
            ErrorState.WrongPassword(exception.errorMessage)
        )

        is AuthorizationException.UserAlreadyExistException -> onError(
            ErrorState.UserAlreadyExists(exception.errorMessage)
        )

        is AuthorizationException.UserNotFoundException -> onError(ErrorState.UserNotFound(exception.errorMessage))
        AuthorizationException.InvalidEmailException -> onError(ErrorState.InvalidEmail)
        AuthorizationException.InvalidFullNameException -> onError(ErrorState.InvalidFullName)
        AuthorizationException.InvalidPhoneException -> onError(ErrorState.InvalidPhone)
        AuthorizationException.InvalidAddressException -> onError(ErrorState.InvalidAddress)
        is AuthorizationException.LocationAccessDeniedException -> onError(ErrorState.LocationPermissionDenied)
    }
}