package org.thechance.service_identity.endpoints.validation

open class IdentityException(val code: Int) : Throwable()

// region address
object InvalidHexStringLengthException : IdentityException(code = ERROR_CODE_INVALID_HEX_STRING_LENGTH)
object InvalidIDException : IdentityException(code = ERROR_CODE_INVALID_ADDRESS_ID)
object InvalidUserIDException : IdentityException(code = ERROR_CODE_INVALID_USER_ID)
object AddressNotFoundException : IdentityException(code = ERROR_CODE_NOT_FOUND)

//endregion

// region user

object InvalidUserNameException: IdentityException(code = ERROR_CODE_INVALID_USERNAME) {
    private fun readResolve(): Any = InvalidUserNameException
}

object InvalidFullNameException: IdentityException(code = ERROR_CODE_INVALID_USERNAME) {
    private fun readResolve(): Any = InvalidFullNameException
}

object UserAlreadyExistsException: IdentityException(code = ERROR_USER_ALREADY_EXISTS) {
    private fun readResolve(): Any = UserAlreadyExistsException
}
// endregion