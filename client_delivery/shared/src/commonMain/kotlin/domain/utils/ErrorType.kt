package domain.utils

// Those Exception is just a sample,need to refactor all these exceptions based on app requirements
open class BpException : Exception()
open class InternetException : BpException()
class NoInternetException : InternetException()

class NotFoundException() : BpException()

open class AuthorizationException(message: String) : BpException()
class UnAuthorizedException(message: String) : AuthorizationException(message)
open class UsernameException(message: String) : AuthorizationException(message)
open class PasswordException(message: String) : AuthorizationException(message)
class InvalidUsernameException(message: String) : UsernameException(message)
class InvalidPasswordException(message: String) : PasswordException(message)

open class InvalidCredentialsException(message: String) : AuthorizationException(message)
class UserNotFoundException(message: String) : AuthorizationException(message)
class UnknownErrorException : BpException()
