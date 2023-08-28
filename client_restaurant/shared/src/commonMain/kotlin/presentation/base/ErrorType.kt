package presentation.base

open class BpException(message: String) : Exception(message)

//region InternetException
open class InternetException(message: String) : BpException(message)
class WifiDisabledException : InternetException("wifi disabled")
class NoInternetException : InternetException("No internet connection")
class NetworkNotSupportedException : InternetException("Network Not Supported")

//endregion
open class AuthorizationException : BpException("")
class UnAuthorizedException : AuthorizationException()
class PermissionDenied : AuthorizationException()

open class RequestException(message: String) : BpException(message)
class ClientSideException : RequestException("")
class ServerSideException : RequestException("")
open class InvalidCredentialsException(message: String) : RequestException(message)
class UserNotFoundException(val errorMessage: String) : RequestException(errorMessage)
class InvalidUserNameException(val errorMessage: String) : InvalidCredentialsException(errorMessage)
class InvalidPasswordException(val errorMessage: String) : InvalidCredentialsException(errorMessage)
class UnknownErrorException : RequestException("Unknown error")