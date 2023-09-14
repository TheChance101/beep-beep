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

        if (errorCodes.contains(1014))
            errors[1014] = localizedMessages.invalidPermission

        if (errorCodes.contains(1015))
            errors[1015] = localizedMessages.alreadyInFavorite

        if (errorCodes.contains(1041))
            errors[1041] = localizedMessages.notFound

        if (errorCodes.contains(1042))
            errors[1042] = localizedMessages.invalidRequestParameter

        if (errorCodes.contains(1043))
            errors[1043] = localizedMessages.userNotFound

        if (errorCodes.contains(1045))
            errors[1045] = localizedMessages.invalidAddressLocation


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


        if(errorCodes.contains(2001))
            errors[2001] = localizedMessages.restaurantInvalidId

        if(errorCodes.contains(2002))
            errors[2002] = localizedMessages.restaurantInvalidName

        if(errorCodes.contains(2003))
            errors[2003] = localizedMessages.restaurantInvalidLocation

        if(errorCodes.contains(2004))
            errors[2004] = localizedMessages.restaurantInvalidDescription

        if(errorCodes.contains(2005))
            errors[2005] = localizedMessages.restaurantInvalidPriceLevel

        if(errorCodes.contains(2006))
            errors[2006] = localizedMessages.restaurantInvalidRate

        if(errorCodes.contains(2007))
            errors[2007] = localizedMessages.restaurantInvalidPhone

        if(errorCodes.contains(2008))
            errors[2008] = localizedMessages.restaurantInvalidTime

        if(errorCodes.contains(2009))
            errors[2009] = localizedMessages.restaurantInvalidPage

        if(errorCodes.contains(2010))
            errors[2010] = localizedMessages.restaurantInvalidPageLimit

        if(errorCodes.contains(2011))
            errors[2011] = localizedMessages.restaurantInvalidOneOrMoreIds

        if(errorCodes.contains(2012))
            errors[2012] = localizedMessages.restaurantInvalidPermissionUpdateLocation

        if(errorCodes.contains(2013))
            errors[2013] = localizedMessages.restaurantInvalidUpdateParameter

        if(errorCodes.contains(2014))
            errors[2014] = localizedMessages.restaurantInvalidPropertyRights

        if(errorCodes.contains(2015))
            errors[2015] = localizedMessages.restaurantInvalidPrice

        if(errorCodes.contains(2016))
            errors[2016] = localizedMessages.restaurantInvalidCuisineLimit

        if(errorCodes.contains(2017))
            errors[2017] = localizedMessages.restaurantInvalidAddress

        if(errorCodes.contains(2018))
            errors[2018] = localizedMessages.restaurantInvalidEmail

        if(errorCodes.contains(2100))
            errors[2100] = localizedMessages.restaurantInvalidRequestParameter

        if(errorCodes.contains(2404))
            errors[2404] = localizedMessages.restaurantNotFound

        if(errorCodes.contains(2405))
            errors[2405] = localizedMessages.restaurantErrorAdd

        if(errorCodes.contains(2500))
            errors[2500] = localizedMessages.restaurantClosed

        if(errorCodes.contains(2501))
            errors[2501] = localizedMessages.restaurantInsertOrderError

        if(errorCodes.contains(2502))
            errors[2502] = localizedMessages.restaurantInvalidReceivedOrders

        if(errorCodes.contains( 2503))
            errors[2503] = localizedMessages.cuisineNameAlreadyExisted

        return errors
    }

}