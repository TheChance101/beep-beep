package domain.usecase


interface IRestaurantInformationValidationUseCase {

    fun isRestaurantNameValid(name: String): Boolean

    fun isOpeningTimeValid(time: String): Boolean

    fun isClosingTimeValid(time: String): Boolean

    fun isDescriptionValid(description: String): Boolean

    fun isPhoneNumberValid(phoneNumber: String): Boolean

    fun isDescriptionLengthValid(description: String): Boolean

    fun isRestaurantInformationValid(
        name: String,
        openingTime: String,
        closingTime: String,
        description: String,
        phoneNumber: String
    ): Boolean

}

class RestaurantInformationValidationUseCase : IRestaurantInformationValidationUseCase {

    override fun isRestaurantNameValid(name: String): Boolean {
        return name.length in 4..25
    }

    override fun isOpeningTimeValid(time: String): Boolean {
        return time.isNotEmpty()
    }

    override fun isClosingTimeValid(time: String): Boolean {
        return time.isNotEmpty()
    }

    override fun isDescriptionValid(description: String): Boolean {
        return description.isNotEmpty()
    }

    override fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.isNotEmpty()
    }

    override fun isDescriptionLengthValid(description: String): Boolean {
        return description.length <= 255
    }

    override fun isRestaurantInformationValid(
        name: String,
        openingTime: String,
        closingTime: String,
        description: String,
        phoneNumber: String
    ): Boolean {
        return isDescriptionLengthValid(description) &&
                isRestaurantNameValid(name) &&
                isOpeningTimeValid(openingTime) &&
                isClosingTimeValid(closingTime) &&
                isDescriptionValid(description) &&
                isPhoneNumberValid(phoneNumber)
    }

}