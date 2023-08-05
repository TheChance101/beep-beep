package org.thechance.service_identity.endpoints.validation

import io.ktor.server.plugins.requestvalidation.*
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

fun String.isHexStringValid(): Boolean {
    return this.length == 24
}