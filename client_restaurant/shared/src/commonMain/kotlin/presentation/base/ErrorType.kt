package presentation.base

open class BpException: Exception()

//region InternetException
open class InternetException: BpException()
class WifiDisabledException: InternetException()
class NoInternetException: InternetException()
class NetworkNotSupportedException: InternetException()
//endregion
open class AuthorizationException: BpException()
class UnAuthorizedException : AuthorizationException()
class PermissionDenied : AuthorizationException()

open class RequestException: BpException()
class ClientSideException: RequestException()
class ServerSideException: RequestException()
class InvalidCredentialsException(val errorMessage: String): RequestException()
class UserNotFoundException(val errorMessage: String): RequestException()
class UnknownErrorException: RequestException()
