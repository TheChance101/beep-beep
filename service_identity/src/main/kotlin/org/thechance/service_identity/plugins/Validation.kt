package org.thechance.service_identity.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.domain.usecases.validation.IAddressValidationUseCase
import org.thechance.service_identity.domain.usecases.validation.IUserValidationUseCase
import org.thechance.service_identity.endpoints.validation.addressValidation
import org.thechance.service_identity.endpoints.validation.createUserValidation
import org.thechance.service_identity.endpoints.validation.updateAddressValidation
import org.thechance.service_identity.endpoints.validation.updateUserValidation

fun Application.configureValidation() {

    val addressValidation: IAddressValidationUseCase by inject()
    val userValidation: IUserValidationUseCase by inject()

    install(RequestValidation) {
        addressValidation(addressValidation)
        createUserValidation(userValidation)
        updateUserValidation(userValidation)
        updateAddressValidation(addressValidation)
    }
}
