package presentation.base

open class BpException: Exception()
open class InternetException: BpException()
class WifiDisabledException: InternetException()
class NoInternetException: InternetException()
class NetworkNotSupportedException: InternetException()


open class AuthorizationException: BpException()
class UnAuthorizedException: AuthorizationException()


open class RequestException: BpException()
class ClientSideException: RequestException()
class ServerSideException: RequestException()