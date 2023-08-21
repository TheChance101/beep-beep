package org.thechance.api_gateway.data.localized_messages.error_codes

interface LocalizedMessages {

    val invalidRequestParameter: String
        get() = ""
    val invalidAddressLocation: String
        get() = ""

    val userAlreadyExist: String
        get() = ""
    val invalidInformation: String
        get() = ""
    val invalidFullName: String
        get() = ""
    val invalidUsername: String
        get() = ""
    val passwordCannotBeLessThan8Characters: String get() = ""
    val usernameCannotBeBlank: String get() = ""
    val passwordCannotBeBlank: String get() = ""
    val invalidEmail: String get() = ""

    val notFound: String get() = ""

    val invalidCredentials: String get() = ""

    val userCreatedSuccessfully: String get() = ""
}