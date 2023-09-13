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
    override val alreadyInFavorite: String = "already in your favorite list"
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
    override val restaurantCreatedSuccessfully: String = "Restaurant created successfully 🥳"
    override val restaurantUpdateSuccessfully: String = "Restaurant updated successfully 🥳"
    override val restaurantDeleteSuccessfully: String = "Restaurant deleted successfully 🥳"
    override val restaurantInvalidId: String = "invalid id"
    override val restaurantInvalidName: String = "invalid name"
    override val restaurantInvalidLocation: String = "invalid location"
    override val restaurantInvalidDescription: String = "invalid description"
    override val restaurantInvalidPriceLevel: String = "invalid price level"
    override val restaurantInvalidRate: String = "invalid rate"
    override val restaurantInvalidPhone: String = "invalid phone"
    override val restaurantInvalidTime: String = "invalid time"
    override val restaurantInvalidPage: String = "invalid page"
    override val restaurantInvalidPageLimit: String = "invalid page limit"
    override val restaurantInvalidOneOrMoreIds: String = "invalid one or more ids"
    override val restaurantInvalidPermissionUpdateLocation: String = "invalid permission update location"
    override val restaurantInvalidUpdateParameter: String = "invalid update parameter"
    override val restaurantInvalidPropertyRights: String = "invalid property rights"
    override val restaurantInvalidPrice: String = "invalid price"
    override val restaurantInvalidCuisineLimit: String = "invalid cuisine limit"
    override val restaurantInvalidAddress: String = "invalid address"
    override val restaurantInvalidEmail: String = "invalid email"
    override val restaurantInvalidRequestParameter: String = "invalid request parameter"
    override val restaurantErrorAdd: String = "error add"
    override val restaurantClosed: String = "restaurant closed"
    override val restaurantInsertOrderError: String = "insert order error"
    override val restaurantInvalidReceivedOrders: String = "invalid received orders"
    override val restaurantNotFound: String = "Sorry, we could not found this restaurant"
    override val deletedSuccessfully: String = "Deleted successfully "
    override val cuisineNameAlreadyExisted: String = "Cuisine name already existed"
    //endregion
}