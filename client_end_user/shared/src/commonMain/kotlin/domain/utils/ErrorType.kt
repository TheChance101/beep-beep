package domain.utils

sealed class InternetException : Exception() {
    class WifiDisabledException : InternetException()
    class NoInternetException : InternetException()
    class NetworkNotSupportedException : InternetException()
}

open class BpException(message: String) : Exception(message)

class UnknownErrorException(message: String) : BpException(message)

class NotFoundException(message: String) : BpException(message)

class SocketClosedException(message: String) : BpException(message)

open class AuthorizationException(val errorMessage: String = "") : BpException(errorMessage) {
    data object UnAuthorizedException : AuthorizationException()
    data object InvalidUsernameException : AuthorizationException()
    data object InvalidPasswordException : AuthorizationException()
    data object InvalidFullNameException : AuthorizationException()
    data object InvalidEmailException : AuthorizationException()
    data object InvalidPhoneException : AuthorizationException()
    data object InvalidAddressException : AuthorizationException()

    class UserNotFoundException(message: String) : AuthorizationException(message)
    class InvalidCredentialsException(message: String) : AuthorizationException(message)
    class UserAlreadyExistException(message: String) : AuthorizationException(message)
    class LocationAccessDeniedException(message: String) : AuthorizationException(message)
}


open class RestaurantException(errorMessage: String = "") : BpException(errorMessage) {
    class CartIsFullException(message: String) : RestaurantException(message)
    class RestaurantClosedException(message: String) : RestaurantException(message)
}