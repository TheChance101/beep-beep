package org.thechance.api_gateway.util


open class IdentityException(message: String) : Throwable(message)

class UserAlreadyExistsException(message: String) : IdentityException(message)

class ResourceNotFoundException(message: String) : IdentityException(message)

class MissingParameterException(message: String) : IdentityException(message)

class InsufficientFundsException(message: String) : IdentityException(message)

class RequestValidationException(list: List<String>) : IdentityException(list.joinToString(","))