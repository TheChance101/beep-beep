package domain.usecase


interface IValidateManageMealUseCase {
    fun isMealNameValid(name: String): Boolean

    fun isDescriptionLengthValid(description: String): Boolean

    fun isPriceValid(price: Double): Boolean
    fun hasAtLeastOneCuisine(cuisines: List<String>): Boolean

    fun isMealInformationValid(
        name: String,
        description: String,
        cuisines: List<String>,
        price: Double,
    ): Boolean

}

class ValidateManageMealUseCase : IValidateManageMealUseCase {
    override fun isMealNameValid(name: String): Boolean {
        // Assuming a valid meal name should not be empty and should not exceed a certain length
        return name.isNotBlank() && name.length <= 50 // You can adjust the length limit as needed
    }

    override fun isDescriptionLengthValid(description: String): Boolean {
        return description.length < 255
    }

    override fun isPriceValid(price: Double): Boolean {
        return try {
            price > 0.0
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun hasAtLeastOneCuisine(cuisines: List<String>): Boolean {
        return cuisines.isNotEmpty()
    }

    override fun isMealInformationValid(
        name: String,
        description: String,
        cuisines: List<String>,
        price: Double
    ): Boolean {
        return isMealNameValid(name) &&
                isDescriptionLengthValid(description) &&
                isPriceValid(price)
    }
}