package domain.usecase


interface IValidateRestaurantInfoUseCase {

    fun isRestaurantNameValid(name: String): Boolean

    fun isTimeValid(time: String): Boolean

    fun isPhoneNumberValid(phoneNumber: String): Boolean

    fun isDescriptionLengthValid(description: String): Boolean

    fun isRestaurantInformationValid(
        name: String,
        openingTime: String,
        closingTime: String,
        phoneNumber: String,
        description: String
    ): Boolean

}

class ValidateRestaurantInfoUseCase : IValidateRestaurantInfoUseCase {

    override fun isRestaurantNameValid(name: String): Boolean {
        return name.length in 4..25
    }

    override fun isTimeValid(time: String): Boolean {
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
        phoneNumber: String,
        description: String
    ): Boolean {
        return isRestaurantNameValid(name) &&
                isTimeValid(openingTime) &&
                isTimeValid(closingTime) &&
                isPhoneNumberValid(phoneNumber) &&
                isDescriptionLengthValid(description)
    }

}