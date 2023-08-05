package org.thechance.service_identity.endpoints.validation

import io.ktor.server.plugins.requestvalidation.*
import org.thechance.service_identity.api.model.UserDto
import org.thechance.service_identity.endpoints.model.AddressDto

fun RequestValidationConfig.addressValidation() {
    validate<AddressDto> { addressDto ->
        val reasons = mutableListOf<String>()

        if (addressDto.userId.isEmpty()) {
            reasons.add(ERROR_CODE_INVALID_USER_ID.toString())
        }

        if (!addressDto.userId.isHexStringValid()){
            reasons.add(ERROR_CODE_INVALID_HEX_STRING_LENGTH.toString())
        }

        if (addressDto.latitude !in -90.0..90.0 || addressDto.longitude !in -180.0..180.0) {
            reasons.add(ERROR_CODE_INVALID_ADDRESS_LOCATION.toString())
        }

        if (reasons.isNotEmpty()) {
            ValidationResult.Invalid(reasons)
        } else {
            ValidationResult.Valid
        }
    }
}


fun RequestValidationConfig.userValidation() {
    validate<UserDto> { user ->

        val reasons = mutableListOf<String>()
        val validUserNameRegex = "[a-zA-Z0-9_]+".toRegex()

        if (user.username?.isBlank() == true) {
            reasons.add(ERROR_CODE_INVALID_USERNAME.toString())
        }
        if (user.username?.matches(validUserNameRegex) != true) {
            reasons.add(ERROR_CODE_INVALID_USERNAME.toString())
        }
        if(user.fullName?.isEmpty() == true) {
            reasons.add(ERROR_CODE_INVALID_FULLNAME.toString())
        }

        if (reasons.isNotEmpty()) {
            ValidationResult.Invalid(reasons)
        } else {
            ValidationResult.Valid
        }

    }
}

fun String.isHexStringValid(): Boolean {
    return this.length == 24
}