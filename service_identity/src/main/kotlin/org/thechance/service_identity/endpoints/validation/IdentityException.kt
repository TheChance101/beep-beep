package org.thechance.service_identity.endpoints.validation

open class IdentityException(val code: Int) : Throwable()

// region address
class InvalidHexStringLengthException : IdentityException(code = ERROR_CODE_INVALID_HEX_STRING_LENGTH)
class InvalidIDException : IdentityException(code = ERROR_CODE_INVALID_ADDRESS_ID)
class InvalidUserIDException : IdentityException(code = ERROR_CODE_INVALID_USER_ID)
class AddressNotFoundException : IdentityException(code = ERROR_CODE_NOT_FOUND)

//endregion

// region user

class InvalidUserNameException: IdentityException(code = ERROR_CODE_INVALID_USERNAME)

class InvalidFullNameException: IdentityException(code = ERROR_CODE_INVALID_USERNAME)

class UserAlreadyExistsException: IdentityException(code = ERROR_USER_ALREADY_EXISTS)
// endregion