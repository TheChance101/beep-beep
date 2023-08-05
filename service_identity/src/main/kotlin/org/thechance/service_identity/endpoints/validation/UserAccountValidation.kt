package org.thechance.service_identity.endpoints.validation

import io.ktor.server.plugins.requestvalidation.*
import org.thechance.service_identity.api.model.request.UpdateUserRequest
import org.thechance.service_identity.endpoints.model.AddressDto
import org.thechance.service_identity.endpoints.model.request.CreateUserRequest

fun RequestValidationConfig.addressValidation() {
    validate<AddressDto> { addressDto ->
        val reasons = mutableListOf<String>()

        if (addressDto.userId.isEmpty()) {
            reasons.add(INVALID_USER_ID)
        }

        if (!addressDto.userId.isHexStringValid()){
            reasons.add(INVALID_HEX_STRING_LENGTH)
        }

        if (addressDto.latitude !in -90.0..90.0 || addressDto.longitude !in -180.0..180.0) {
            reasons.add(INVALID_ADDRESS_LOCATION)
        }

        if (reasons.isNotEmpty()) {
            ValidationResult.Invalid(reasons)
        } else {
            ValidationResult.Valid
        }
    }
}


fun RequestValidationConfig.createUserValidation() {
    validate<CreateUserRequest> { user ->

        val reasons = mutableListOf<String>()
        val validUserNameRegex = "[a-zA-Z0-9_]+".toRegex()

        if (user.username.isBlank()) {
            reasons.add(USERNAME_CANNOT_BE_BLANK)
        }
        if (!user.username.matches(validUserNameRegex)) {
            reasons.add(INVALID_USERNAME)
        }
        if (user.fullName.isEmpty()) {
            reasons.add(INVALID_FULLNAME)
        }
        if (user.password.isEmpty()) {
            reasons.add(PASSWORD_CANNOT_BE_BLANK)
        }
        if (user.password.length < 8) {
            reasons.add(PASSWORD_CANNOT_BE_LESS_THAN_8_CHARACTERS)
        }

        if (reasons.isNotEmpty()) {
            ValidationResult.Invalid(reasons)
        } else {
            ValidationResult.Valid
        }

    }
}

fun RequestValidationConfig.updateUserValidation() {
    validate<UpdateUserRequest> { user ->

        val reasons = mutableListOf<String>()
        val validUserNameRegex = "[a-zA-Z0-9_]+".toRegex()

        if (user.username?.isBlank() ?: false) {
            reasons.add(USERNAME_CANNOT_BE_BLANK)
        }
        if (user.username?.matches(validUserNameRegex) ?: false) {
            reasons.add(INVALID_USERNAME)
        }
        if (user.fullName?.isEmpty() ?: false) {
            reasons.add(INVALID_FULLNAME)
        }
        if (user.password?.isEmpty() ?: false) {
            reasons.add(PASSWORD_CANNOT_BE_BLANK)
        }
        if ((user.password?.length ?: 8) < 8) {
            reasons.add(PASSWORD_CANNOT_BE_LESS_THAN_8_CHARACTERS)
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