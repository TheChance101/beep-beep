package org.thechance.common.domain.usecase

import java.util.regex.Pattern

interface IValidateRestaurantUseCase {
    fun validateRestaurantName(name: String): Boolean
    fun validateUserName(name: String): Boolean
    fun validateUserPassword(name: String): Boolean
    fun validateNumber(number: String): Boolean
    fun validateStartTime(hour: String): Boolean
    fun validateEndTime(hour: String): Boolean
    fun validateLocation(location: String): Boolean
}

class ValidateRestaurantUseCase():IValidateRestaurantUseCase{
    override fun validateRestaurantName(name: String): Boolean {
        return name.isNotBlank() && name.length > 2 && name.any { it.isLetter() }
    }

    override fun validateUserName(name: String): Boolean {
        return name.isNotBlank() && name.length > 5 && name.any { it.isLetter() }
    }

    override fun validateUserPassword(name: String): Boolean {
        return name.isNotBlank() && name.length > 5 && name.any { !it.isLetter() && !it.isDigit() }
    }

    override fun validateNumber(number: String): Boolean {
        val egypt = "^\\+201[0125][0-9]{8}$"
        val iraq = "^\\+964(7[0178]|78)\\d{4}\\d{4}$"
        val jordan = "^\\+962[67]\\d{7}$"
        val palestine = "^\\+970[0-9]{10}$"
        val syria = "^\\+963[0-9]{10}$"

        val result = when {
            Pattern.compile(egypt).matcher(number).matches() -> true
            Pattern.compile(iraq).matcher(number).matches() -> true
            Pattern.compile(jordan).matcher(number).matches() -> true
            Pattern.compile(palestine).matcher(number).matches() -> true
            Pattern.compile(syria).matcher(number).matches() -> true
            else -> false
        }
        return result
    }

    override fun validateStartTime(hour: String): Boolean {
        return Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]\$").matcher(hour).matches() && hour.isNotBlank()
    }

    override fun validateEndTime(hour: String): Boolean {
        return Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]\$").matcher(hour).matches() && hour.isNotBlank()
    }

    override fun validateLocation(location: String): Boolean {
        val loc = Pattern.compile("^((-?|\\+?)?\\d+(\\.\\d+)?),\\s*((-?|\\+?)?\\d+(\\.\\d+)?)\$")
        return location.isNotBlank() && loc.matcher(location).matches()
    }
}