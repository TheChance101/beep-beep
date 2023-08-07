package org.thechance.service_identity.domain.entity


open class IdentityException(message: String) : Throwable(message)

class UserAlreadyExistsException(message: String) : IdentityException(message)

class ResourceNotFoundException(message: String) : IdentityException(message)

class MissingParameterException(message: String) : IdentityException(message)