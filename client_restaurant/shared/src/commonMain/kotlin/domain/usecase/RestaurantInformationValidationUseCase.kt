package domain.usecase

import domain.entity.Time

interface IRestaurantInformationValidationUseCase {

    fun isRestaurantNameEmpty(name: String): Boolean

    fun isOpeningTimeEmpty(time: Time): Boolean

    fun isClosingTimeEmpty(time: Time): Boolean

    fun isDescriptionEmpty(description: String): Boolean

    fun isPhoneNumberEmpty(phoneNumber: String): Boolean

    fun isValidDescriptionLength(description: String): Boolean

    fun isValidRestaurantName(name: String): Boolean

    fun isRestaurantInformationValid(
        name: String,
        openingTime: Time,
        closingTime: Time,
        description: String,
        phoneNumber: String
    ): Boolean

}

class RestaurantInformationValidationUseCase : IRestaurantInformationValidationUseCase {

    override fun isRestaurantNameEmpty(name: String): Boolean {
        return name.isEmpty()
    }

    override fun isOpeningTimeEmpty(time: Time): Boolean {
        return time.hour == 0 && time.minute == 0 && time.second == 0
    }

    override fun isClosingTimeEmpty(time: Time): Boolean {
        return time.hour == 0 && time.minute == 0 && time.second == 0
    }

    override fun isDescriptionEmpty(description: String): Boolean {
        return description.isEmpty()
    }

    override fun isPhoneNumberEmpty(phoneNumber: String): Boolean {
        return phoneNumber.isEmpty()
    }

    override fun isValidDescriptionLength(description: String): Boolean {
        return description.length <= 255
    }

    override fun isValidRestaurantName(name: String): Boolean {
        return name.length <= 25
    }

    override fun isRestaurantInformationValid(
        name: String,
        openingTime: Time,
        closingTime: Time,
        description: String,
        phoneNumber: String
    ): Boolean {
        return isValidDescriptionLength(description) &&
                isValidRestaurantName(name) &&
                !isRestaurantNameEmpty(name) &&
                !isOpeningTimeEmpty(openingTime) &&
                !isClosingTimeEmpty(closingTime) &&
                !isDescriptionEmpty(description) &&
                !isPhoneNumberEmpty(phoneNumber)
    }

}