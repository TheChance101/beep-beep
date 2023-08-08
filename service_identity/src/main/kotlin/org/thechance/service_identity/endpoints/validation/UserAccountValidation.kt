package org.thechance.service_identity.endpoints.validation

import io.ktor.server.plugins.requestvalidation.*
import org.thechance.service_identity.domain.usecases.validation.IAddressValidationUseCase
import org.thechance.service_identity.domain.usecases.validation.IUserValidationUseCase
import org.thechance.service_identity.endpoints.model.AddressDto
import org.thechance.service_identity.endpoints.model.request.CreateUserRequest
import org.thechance.service_identity.endpoints.model.request.UpdateUserRequest

fun RequestValidationConfig.addressValidation(addressValidation: IAddressValidationUseCase) {

    validate<AddressDto> { addressDto ->
        val reasons = mutableListOf<String>()

        if (!addressValidation.validateUserIdNotEmpty(addressDto.userId)) {
            reasons.add(INVALID_USER_ID)
        }

        if (!addressValidation.validateUserIdHexLength(addressDto.userId)) {
            reasons.add(INVALID_HEX_STRING_LENGTH)
        }

        if (!addressValidation.validateLocation(addressDto.location.latitude, addressDto.location.longitude)) {
            reasons.add(INVALID_ADDRESS_LOCATION)
        }

        if (reasons.isNotEmpty()) {
            ValidationResult.Invalid(reasons)
        } else {
            ValidationResult.Valid
        }
    }
}


fun RequestValidationConfig.createUserValidation(userValidation: IUserValidationUseCase) {
    validate<CreateUserRequest> { user ->

        val reasons = mutableListOf<String>()

        if (!userValidation.validateUsernameIsNotBlank(user.username)) {
            reasons.add(USERNAME_CANNOT_BE_BLANK)
        }

        if (!userValidation.validateUsername(user.username)) {
            reasons.add(INVALID_USERNAME)
        }

        if (!userValidation.validateFullNameIsNotEmpty(user.fullName)) {
            reasons.add(INVALID_FULLNAME)
        }

        if (!userValidation.validatePasswordIsNotEmpty(user.password)) {
            reasons.add(PASSWORD_CANNOT_BE_BLANK)
        }

        if (!userValidation.validatePasswordLength(user.password)) {
            reasons.add(PASSWORD_CANNOT_BE_LESS_THAN_8_CHARACTERS)
        }

        if (reasons.isNotEmpty()) {
            ValidationResult.Invalid(reasons)
        } else {
            ValidationResult.Valid
        }

    }
}

fun RequestValidationConfig.updateUserValidation(userValidation: IUserValidationUseCase) {
    validate<UpdateUserRequest> { user ->

        val reasons = mutableListOf<String>()

        if (!userValidation.validateUsernameIsNotBlank(user.username)) {
            reasons.add(USERNAME_CANNOT_BE_BLANK)
        }

        if (!userValidation.validateUsername(user.username)) {
            reasons.add(INVALID_USERNAME)
        }

        if (!userValidation.validateFullNameIsNotEmpty(user.fullName)) {
            reasons.add(INVALID_FULLNAME)
        }

        if (!userValidation.validatePasswordIsNotEmpty(user.password)) {
            reasons.add(PASSWORD_CANNOT_BE_BLANK)
        }

        if (!userValidation.validatePasswordLength(user.password)) {
            reasons.add(PASSWORD_CANNOT_BE_LESS_THAN_8_CHARACTERS)
        }

        if (reasons.isNotEmpty()) {
            ValidationResult.Invalid(reasons)
        } else {
            ValidationResult.Valid
        }

    }
}