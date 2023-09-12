package domain

open class BpException(message: String) : Exception(message)

//region InternetException
open class InternetException(message: String) : BpException(message)
class NoInternetException : InternetException("No internet connection")
//endregion

//region authorization
open class AuthorizationException(message: String) : BpException(message)
class UnAuthorizedException : AuthorizationException("You have to login")
class PermissionDenied : AuthorizationException("You have to allow permission")
//endregion

class ServerSideException(message: String) : BpException(message)

class NotFoundedException : BpException("Not founded")

class UnKnownErrorException(message: String) : BpException(message)

class UserNotFoundException(val errorMessage: String) : BpException(errorMessage)

class InvalidCredentialsException(message: String) : BpException(message)

class InvalidUserNameException(private val errorMessage: String) : BpException(errorMessage)

class InvalidPasswordException(private val errorMessage: String) : BpException(errorMessage)