package org.thechance.api_gateway.data.gateway

import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.localized_messages.LocalizedMessagesFactory
import java.util.*

@Single
class ErrorHandler(
    private val localizedMessagesFactory: LocalizedMessagesFactory
) {

    fun getLocalizedErrorMessage(errorCodes: List<Int>, locale: Locale): String {
        val localizedMessages = localizedMessagesFactory.createLocalizedMessages(locale)
        return if (errorCodes.contains(1002))
            localizedMessages.userAlreadyExist
        else if (errorCodes.any { it == 1003 })
            localizedMessages.invalidUsername
        else if (errorCodes.any { it == 1004 })
            localizedMessages.invalidFullName
        else if (errorCodes.any { it == 1005 })
            localizedMessages.passwordCannotBeLessThan8Characters
        else if (errorCodes.any { it == 1006 })
            localizedMessages.usernameCannotBeBlank
        else if (errorCodes.any { it == 1007 })
            localizedMessages.passwordCannotBeBlank
        else if (errorCodes.any { it == 1008 })
            localizedMessages.invalidEmail
        else if (errorCodes.any { it == 1013 })
            localizedMessages.invalidCredentials
        else if (errorCodes.any { it == 1041 })
            localizedMessages.notFound
        else if (errorCodes.any { it == 1042 })
            localizedMessages.invalidRequestParameter
        else if (errorCodes.any { it == 1045 })
            localizedMessages.invalidAddressLocation
        else ""
    }

}