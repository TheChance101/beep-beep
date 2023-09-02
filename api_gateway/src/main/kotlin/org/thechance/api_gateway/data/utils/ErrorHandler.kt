package org.thechance.api_gateway.data.utils

import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory

@Single
class ErrorHandler(
    private val localizedMessagesFactory: LocalizedMessagesFactory
) {

    fun getLocalizedErrorMessage(errorCodes: List<Int>, languageCode: String): Map<Int, String> {
        val localizedMessages = localizedMessagesFactory.createLocalizedMessages(languageCode)

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

        if (errorCodes.contains(2404)){
            errors[2404] = localizedMessages.restaurantNotFound
        }

        if (errorCodes.contains(8000))
            errors[8000] = localizedMessages.invalidPermission

        if(errorCodes.contains(3001))
            errors[3001] = localizedMessages.invalidId
        if(errorCodes.contains(3002))
            errors[3002] = localizedMessages.invalidPlate
        if(errorCodes.contains(3003))
            errors[3003] = localizedMessages.invalidColor
        if(errorCodes.contains(3004))
            errors[3004] = localizedMessages.invalidCarType
        if(errorCodes.contains(3005))
            errors[3005] = localizedMessages.seatOutOfRange
        if(errorCodes.contains(3006))
            errors[3006] = localizedMessages.invalidLocation
        if(errorCodes.contains(3007))
            errors[3007] = localizedMessages.invalidRate
        if(errorCodes.contains(3008))
            errors[3008] = localizedMessages.invalidDate
        if(errorCodes.contains(3009))
            errors[3009] = localizedMessages.invalidPrice
        if(errorCodes.contains(3010))
            errors[3010] = localizedMessages.alreadyExist
        if(errorCodes.contains(3100))
            errors[3100] = localizedMessages.invalidRequestParameter
        if(errorCodes.contains(3200))
            errors[3200] = localizedMessages.requiredQuery
        if(errorCodes.contains(3404))
            errors[3404] = localizedMessages.notFound
        if(errorCodes.contains(3911))
            errors[3911] = localizedMessages.unknownError

        return errors
    }

}