package domain.utils

open class BpException : Exception()
open class InternetException : BpException()
class WifiDisabledException : InternetException()
class NoInternetException : InternetException()
class NetworkNotSupportedException : InternetException()

class PermissionDenied : AuthorizationException()
open class RequestException : BpException()

open class AuthorizationException : BpException()
class UnAuthorizedException : AuthorizationException()
open class UsernameException : AuthorizationException()
open class PasswordException : AuthorizationException()
class InvalidUsernameException : UsernameException()
class InvalidPasswordException : PasswordException()
