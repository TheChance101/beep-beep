package org.thechance.common.domain.usecase

interface ITaxiValidationUseCase {

    fun isValidPlateNumber(plateNumber: String): Boolean

    fun isValidCarModel(model: String): Boolean

    fun isFormValid(plateNumber: String, model: String): Boolean {
        return isValidPlateNumber(plateNumber) && isValidCarModel(model)
    }
}

class TaxiValidationUseCase : ITaxiValidationUseCase {

    override fun isValidPlateNumber(plateNumber: String): Boolean {
        return PLATE_NUMBER_REGEX.any { it.matches(plateNumber.trim()) }
    }

    override fun isValidCarModel(model: String): Boolean {
        val trimmedModel = model.trim()
        return trimmedModel.length in MIN_MODEL_LENGTH..MAX_MODEL_LENGTH && trimmedModel.all { it.isLetter() }
    }

    private companion object {
        const val MIN_MODEL_LENGTH = 1
        const val MAX_MODEL_LENGTH = 20

        val PLATE_NUMBER_REGEX = listOf(
            "^\\d{5}\\s[A-Z]{3}\$".toRegex(), // Saudi Arabia
            "^[A-Z]{3}\\s\\d{5}\$".toRegex(), // United Arab Emirates
            "^\\d{4}\\s[A-Z]{3}\$".toRegex(), // Egypt
            "^[A-Z]{2}\\s\\d{4}\$".toRegex(), // Jordan
            "^\\d{6}\\s[A-Z]{2}\$".toRegex(), // Lebanon
            "^\\d{4}\\s[A-Z]{2}\$".toRegex(), // Morocco
            "^\\d{3}\\s[A-Z]{2}\$".toRegex(), // Tunisia
            "^[A-Z0-9]{1,2}-[0-9]{4,5}\$".toRegex(), // Iraq and Syria
            "^[A-Z0-9]{1,7}\$".toRegex(), // United States
        )
    }
}