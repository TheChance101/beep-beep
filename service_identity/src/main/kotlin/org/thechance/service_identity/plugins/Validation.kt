package org.thechance.service_identity.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import org.thechance.service_identity.endpoints.validation.addressValidation
import org.thechance.service_identity.endpoints.validation.createUserValidation
import org.thechance.service_identity.endpoints.validation.updateUserValidation

fun Application.configureValidation() {
    install(RequestValidation) {
        addressValidation()
        createUserValidation()
        updateUserValidation()
    }
}
