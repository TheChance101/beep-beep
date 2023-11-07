package domain.utils

sealed class InternetException : Exception() {
    class WifiDisabledException : InternetException()
    class NoInternetException : InternetException()
    class NetworkNotSupportedException : InternetException()
}


sealed class AuthorizationException(val errorMessage: String = "") : Exception() {
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

sealed class GeneralException : Exception() {
    data object UnknownErrorException : GeneralException()
    data object NotFoundException : GeneralException()
}