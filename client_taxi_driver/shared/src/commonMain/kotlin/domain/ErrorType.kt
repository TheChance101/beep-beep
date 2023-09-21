package domain

open class BpException(message: String?) : Exception(message)

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

class LocationPermissionDeniedException(message: String?) : BpException(message)

class LocationPermissionDeniedAlwaysException(message: String?) : BpException(message)

class UnknownErrorException(message: String) : BpException(message)

class InvalidCredentialsException(message: String) : BpException(message)

class InvalidUserNameException(private val errorMessage: String) : BpException(errorMessage)

class InvalidPasswordException(private val errorMessage: String) : BpException(errorMessage)

class InvalidDriverNameException(private val errorMessage: String) : BpException(errorMessage)

class InvalidDriverEmailException(private val errorMessage: String) : BpException(errorMessage)

class InvalidDescriptionException(private val errorMessage: String) : BpException(errorMessage)
