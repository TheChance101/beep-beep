package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single

@Single
class EnglishLocalizedMessages() : LocalizedMessages {

    // region identity
    override val invalidRequestParameter: String = "Invalid request parameter"
    override val invalidAddressLocation: String = "Invalid address location"
    override val userAlreadyExist: String = "User already exists"
    override val invalidInformation: String = "Invalid information"
    override val invalidFullName: String = "Invalid FullName"
    override val invalidUsername: String = "Invalid username"
    override val passwordCannotBeLessThan8Characters: String = "Password cannot be less than 8 characters"
    override val usernameCannotBeBlank: String = "Username cannot be blank"
    override val passwordCannotBeBlank: String = "Password cannot be blank"
    override val invalidEmail: String = "Invalid email"
    override val notFound: String = "Not found"
    override val invalidCredentials: String = "Invalid credentials"
    override val userCreatedSuccessfully: String = "User created successfully 🎉"
    override val unknownError: String = "Unknown error `¯\\_(ツ)_/¯`"
    override val userNotFound: String = "User not found"
    override val invalidPermission: String = "Invalid permission"
    // endregion

    // region taxi
    override val taxiCreatedSuccessfully: String = "Taxi created successfully 🎉"
    override val taxiUpdateSuccessfully: String = "Taxi updated successfully 🎉"
    override val taxiDeleteSuccessfully: String = "Taxi deleted successfully 🎉"
    override val invalidId: String = "Invalid id"
    override val invalidPlate: String = "Invalid plate"
    override val invalidColor: String = "Invalid color"
    override val invalidCarType: String = "Invalid car type"
    override val seatOutOfRange: String = "Seat out of range"
    override val invalidLocation: String= "Invalid location"
    override val invalidRate: String= "Invalid rate"
    override val invalidDate: String= "Invalid date"
    override val invalidPrice: String= "Invalid price"
    override val alreadyExist: String= "Already exist"
    override val requiredQuery: String= "Required query"
    // endregion

    //region restaurant
    override val restaurantNotFound: String = "Sorry, we could not found this restaurant"
    //endregion
}