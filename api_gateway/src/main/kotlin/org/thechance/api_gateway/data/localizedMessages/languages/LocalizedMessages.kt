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

    val notFound: String

    val invalidCredentials: String

    val userCreatedSuccessfully: String

    val unknownError: String

    val userNotFound: String

    val invalidPermission: String
    // endregion

    // region taxi
    val taxiCreatedSuccessfully: String

    val taxiUpdateSuccessfully: String

    val taxiDeleteSuccessfully: String

    val invalidId : String

    val invalidPlate: String

    val invalidColor : String

    val invalidCarType : String

    val seatOutOfRange : String

    val invalidLocation : String

    val invalidRate : String

    val invalidDate : String

    val invalidPrice : String

    val alreadyExist : String

    val requiredQuery : String



    // endregion

    //region restaurant
    val restaurantNotFound:String
    //endregion
}