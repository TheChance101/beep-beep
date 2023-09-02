package org.thechance.common.domain.util

open class BeepBeepException(message: String) : Exception(message)

class InvalidCredentialsException(message: String) : BeepBeepException(message)

class UnknownErrorException : BeepBeepException("Unknown error")

class NoInternetException : BeepBeepException("No internet connection")

class UserNotFoundException(message: String) : BeepBeepException(message)