package domain.usecase


interface IRestaurantInformationValidationUseCase {

    fun isRestaurantNameValid(name: String): Boolean

    fun isOpeningTimeValid(time: String): Boolean

    fun isClosingTimeValid(time: String): Boolean

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
        val regex = Regex("^([0-1][0-9]|2[0-3]):([0-5][0-9])$")
        return regex.matches(time)
    }

    override fun isClosingTimeValid(time: String): Boolean {
        val regex = Regex("^([0-1][0-9]|2[0-3]):([0-5][0-9])$")
        return regex.matches(time)
    }

    override fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.isNotEmpty()
    }

    override fun isDescriptionLengthValid(description: String): Boolean {
        return description.length < 255
    }

    override fun isRestaurantInformationValid(
        name: String,
        openingTime: String,
        closingTime: String,
        description: String,
        phoneNumber: String
    ): Boolean {
        return isRestaurantNameValid(name) &&
                isOpeningTimeValid(openingTime) &&
                isClosingTimeValid(closingTime) &&
                isDescriptionLengthValid(description) &&
                isPhoneNumberValid(phoneNumber) &&
                isDescriptionLengthValid(description)
    }

}