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
    // endregion

    //region restaurant
    val restaurantNotFound:String
    //endregion
}