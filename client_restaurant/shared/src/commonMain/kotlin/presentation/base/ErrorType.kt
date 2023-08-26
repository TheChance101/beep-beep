package presentation.base

open class BpException(message: String) : Exception(message)

//region InternetException
open class InternetException : BpException("")
class WifiDisabledException : InternetException()
class NoInternetException : InternetException()
class NetworkNotSupportedException : InternetException()

//endregion
open class AuthorizationException : BpException("")
class UnAuthorizedException : AuthorizationException()
class PermissionDenied : AuthorizationException()

open class RequestException(message: String) : BpException(message)
class ClientSideException : RequestException("")
class ServerSideException : RequestException("")
open class InvalidCredentialsException(message: String) : RequestException(message)
class InvalidUserNameException(val errorMessage: String) : InvalidCredentialsException(errorMessage)
class InvalidPasswordException(val errorMessage: String) : InvalidCredentialsException(errorMessage)
class UnknownErrorException : RequestException("")
