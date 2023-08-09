package org.thechance.service_identity.endpoints.validation

import io.ktor.server.plugins.requestvalidation.*
import org.thechance.service_identity.data.collection.CreateUserRequest
import org.thechance.service_identity.data.collection.UpdateUserRequest
import org.thechance.service_identity.domain.usecases.validation.IAddressValidationUseCase
import org.thechance.service_identity.domain.usecases.validation.IUserValidationUseCase
import org.thechance.service_identity.endpoints.model.CreateAddressRequest
import org.thechance.service_identity.endpoints.model.UpdateAddressRequest

fun RequestValidationConfig.addressValidation(addressValidation: IAddressValidationUseCase) {

    validate<CreateAddressRequest> { addressDto ->
        val reasons = mutableListOf<String>()

        if (!addressValidation.validateLocation(addressDto.location.latitude, addressDto.location.longitude)) {
            reasons.add(INVALID_ADDRESS_LOCATION)
        }

        responseWithResult(reasons)
    }
}

fun RequestValidationConfig.updateAddressValidation(addressValidation: IAddressValidationUseCase) {

    validate<UpdateAddressRequest> { addressDto ->
        val reasons = mutableListOf<String>()

        if (addressDto.location?.longitude != null && addressDto.location.latitude != null) {
            if (!addressValidation.validateLocation(addressDto.location.latitude, addressDto.location.longitude)) {
                reasons.add(INVALID_ADDRESS_LOCATION)
            }
        }

        responseWithResult(reasons)
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

        if (!userValidation.validateEmail(user.email)) {
            reasons.add(INVALID_EMAIL)
        }

        responseWithResult(reasons)

    }
}

fun RequestValidationConfig.updateUserValidation(userValidation: IUserValidationUseCase) {
    validate<UpdateUserRequest> { user ->

        val reasons = mutableListOf<String>()

        user.username?.let {
            if (!userValidation.validateUsername(it)) {
                reasons.add(INVALID_USERNAME)
            }
        }

        user.username?.let {
            if (!userValidation.validateUsernameIsNotBlank(it)) {
                reasons.add(USERNAME_CANNOT_BE_BLANK)
            }
        }

        user.fullName?.let {
            if (!userValidation.validateFullNameIsNotEmpty(it)) {
                reasons.add(INVALID_FULLNAME)
            }
        }

        user.password?.let {
            if (!userValidation.validatePasswordIsNotEmpty(it)) {
                reasons.add(PASSWORD_CANNOT_BE_BLANK)
            }
        }

        user.password?.let {
            if (!userValidation.validatePasswordLength(it)) {
                reasons.add(PASSWORD_CANNOT_BE_LESS_THAN_8_CHARACTERS)
            }
        }

        user.email?.let {
            if (!userValidation.validateEmail(it)) {
                reasons.add(INVALID_EMAIL)
            }
        }


        responseWithResult(reasons)

    }
}

private fun responseWithResult(reasons: MutableList<String>) = if (reasons.isNotEmpty()) {
    ValidationResult.Invalid(reasons)
} else {
    ValidationResult.Valid
}