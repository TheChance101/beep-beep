package org.thechance.api_gateway.data.gateway

import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.localized_messages.LocalizedMessagesFactory
import java.util.*

@Single
class ErrorHandler(
    private val localizedMessagesFactory: LocalizedMessagesFactory
) {

    fun getLocalizedErrorMessage(errorCodes: List<Int>, locale: Locale): Map<Int, String> {
        val localizedMessages = localizedMessagesFactory.createLocalizedMessages(locale)

        val errors = mutableMapOf<Int, String>()

        if (errorCodes.contains(1002))
            errors[1002] = localizedMessages.userAlreadyExist

        if (errorCodes.contains(1003))
            errors[1003] = localizedMessages.invalidUsername

        if (errorCodes.contains(1004))
            errors[1004] = localizedMessages.invalidFullName

        if (errorCodes.contains(1005))
            errors[1005] = localizedMessages.passwordCannotBeLessThan8Characters

        if (errorCodes.contains(1006))
            errors[1006] = localizedMessages.usernameCannotBeBlank

        if (errorCodes.contains(1007))
            errors[1007] = localizedMessages.passwordCannotBeBlank

        if (errorCodes.contains(1008))
            errors[1008] = localizedMessages.invalidEmail

        if (errorCodes.contains(1013))
            errors[1013] = localizedMessages.invalidCredentials

        if (errorCodes.contains(1041))
            errors[1041] = localizedMessages.notFound

        if (errorCodes.contains(1042))
            errors[1042] = localizedMessages.invalidRequestParameter

        if (errorCodes.contains(1043))
            errors[1043] = localizedMessages.userNotFound

        if (errorCodes.contains(1045))
            errors[1045] = localizedMessages.invalidAddressLocation

        return errors
    }

}