package org.thechance.api_gateway.data.gateway

import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.localized_messages.LocalizedMessagesFactory
import java.util.*

@Single
class ErrorHandler(
    private val localizedMessagesFactory: LocalizedMessagesFactory
) {

    fun getLocalizedErrorMessage(errorCodes: List<Int>, locale: Locale): List<Map<Int, String>> {
        val localizedMessages = localizedMessagesFactory.createLocalizedMessages(locale)

        val errors = mutableListOf<Map<Int, String>>()

        if (errorCodes.contains(1002))
            errors.add(mapOf(1002 to localizedMessages.userAlreadyExist))

        if (errorCodes.contains(1003))
            errors.add(mapOf(1003 to localizedMessages.invalidUsername))

        if (errorCodes.contains(1004))
            errors.add(mapOf(1004 to localizedMessages.invalidFullName))

        if (errorCodes.contains(1005))
            errors.add(mapOf(1005 to localizedMessages.passwordCannotBeLessThan8Characters))

        if (errorCodes.contains(1006))
            errors.add(mapOf(1006 to localizedMessages.usernameCannotBeBlank))

        if (errorCodes.contains(1007))
            errors.add(mapOf(1007 to localizedMessages.passwordCannotBeBlank))

        if (errorCodes.contains(1008))
            errors.add(mapOf(1008 to localizedMessages.invalidEmail))

        if (errorCodes.contains(1013))
            errors.add(mapOf(1013 to localizedMessages.invalidCredentials))

        if (errorCodes.contains(1041))
            errors.add(mapOf(1041 to localizedMessages.notFound))

        if (errorCodes.contains(1042))
            errors.add(mapOf(1042 to localizedMessages.invalidRequestParameter))

        if (errorCodes.contains(1045))
            errors.add(mapOf(1045 to localizedMessages.invalidAddressLocation))

        return errors
    }

}