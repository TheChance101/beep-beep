package org.thechance.api_gateway.data.localizedMessages.languages


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

    val invalidPhone: String

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

    val tripCreatedSuccessfully: String

    val tripUpdated: String

    val tripCanceled: String

    val tripFinished: String

    val tripArrived: String

    val receivedNewTrip: String

    val newOrderComing: String

    val receivedNewDeliveryOrder: String

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

    val rideApproved: String

    val taxiArrivedToUserLocation: String

    val taxiArrivedToDestination: String

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

    val invalidQuantity: String

    val cartIsAlreadyEmpty: String

    val missingParameter: String

    val tokensNotFound: String

    val tokenNotRegister: String

    val alreadyUpdated: String

    val cancelOrderError: String

    val orderApproved: String

    val orderCanceled: String

    val orderInCooking: String

    val orderFinished: String

    val newOrderTitle: String

    val newOrderBody: String

    val orderApprovedFromDelivery: String

    val orderArrivedToRestaurant: String

    val orderArrivedToClient: String

    //endregion


    // region notification
    val notificationNotSent: String
    //endregion


    //region chat
    val supportAgentNotFound: String
    //endregion
}