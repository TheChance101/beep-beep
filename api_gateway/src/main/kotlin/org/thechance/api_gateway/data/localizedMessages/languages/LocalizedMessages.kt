package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single


interface LocalizedMessages {

    // region identity
    val invalidRequestParameter: String

    val invalidAddressLocation: String

    val userAlreadyExist: String

    val invalidInformation: String

    val invalidFullName: String

    val invalidUsername: String

    val passwordCannotBeLessThan8Characters: String

    val usernameCannotBeBlank: String

    val passwordCannotBeBlank: String

    val invalidEmail: String

    val notFound: String

    val invalidCredentials: String

    val userCreatedSuccessfully: String

    val unknownError: String

    val userNotFound: String

    val invalidPermission: String

    val alreadyInFavorite: String

    // endregion

    // region taxi
    val taxiCreatedSuccessfully: String

    val taxiUpdateSuccessfully: String

    val taxiDeleteSuccessfully: String

    val invalidId: String

    val invalidPlate: String

    val invalidColor: String

    val invalidCarType: String

    val seatOutOfRange: String

    val invalidLocation: String

    val invalidRate: String

    val invalidDate: String

    val invalidPrice: String

    val alreadyExist: String

    val requiredQuery: String


    // endregion

    //region restaurant
    val restaurantCreatedSuccessfully: String

    val restaurantUpdateSuccessfully: String

    val restaurantDeleteSuccessfully: String

    val restaurantInvalidId: String

    val restaurantInvalidName: String

    val restaurantInvalidLocation: String

    val restaurantInvalidDescription: String

    val restaurantInvalidPriceLevel: String

    val restaurantInvalidRate: String

    val restaurantInvalidPhone: String

    val restaurantInvalidTime: String

    val restaurantInvalidPage: String

    val restaurantInvalidPageLimit: String

    val restaurantInvalidOneOrMoreIds: String

    val restaurantInvalidPermissionUpdateLocation: String

    val restaurantInvalidUpdateParameter: String

    val restaurantInvalidPropertyRights: String

    val restaurantInvalidPrice: String

    val restaurantInvalidCuisineLimit: String

    val restaurantInvalidAddress: String

    val restaurantInvalidEmail: String

    val restaurantInvalidRequestParameter: String

    val restaurantNotFound: String

    val restaurantErrorAdd: String

    val restaurantClosed: String

    val restaurantInsertOrderError: String

    val restaurantInvalidReceivedOrders: String

    val deletedSuccessfully: String

    val cuisineNameAlreadyExisted: String

    //endregion
}