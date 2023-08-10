package org.thechance.service_restaurant.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.MealDetails

class MealValidationTest {

    @Test
    fun `should pass validation when add meal with valid details`() {
        //given a valid meal
        val validMeal = createValidMeal()
        // No exception should be thrown
        val mealExecutable = Executable {
            validateAddMeal(validMeal)
        }
        //then no exception should be thrown and pass validation
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should throw exception when add meal with name is empty`() {
        // given a meal with name is empty
        val invalidNameMeal = createValidMeal().copy(name = " ")
        val nameExecutable = Executable { validateAddMeal(invalidNameMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when add meal with name is long`() {
        //given a meal with name is more than 25
        val invalidNameMeal = createValidMeal().copy(name = "A".repeat(26))
        val nameExecutable = Executable { validateAddMeal(invalidNameMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when add meal with name is short`() {
        // given a meal with name is less than four letter
        val invalidNameMeal = createValidMeal().copy(name = "A")
        val nameExecutable = Executable { validateAddMeal(invalidNameMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when add meal with invalid restaurant Id`() {
        val invalidRestaurantIdMeal = createValidMeal().copy(restaurantId = "invalid_id")
        val restaurantIdExecutable = Executable { validateAddMeal(invalidRestaurantIdMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, restaurantIdExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should throw exception when add meal with empty cuisines`() {
        // given a meal with empty cuisines
        val emptyCuisinesMeal = createValidMeal().copy(cuisines = emptyList())
        val cuisineExecutable = Executable { validateAddMeal(emptyCuisinesMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, cuisineExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_REQUEST_PARAMETER))
    }

    @Test
    fun `should throw exception when add meal with invalid cuisine Id`() {
        // given a meal with invalid cuisine Id
        val invalidCuisineIdMeal = createValidMeal().copy(cuisines = listOf(Cuisine("invalid_id", "Italian")))
        val cuisineExecutable = Executable { validateAddMeal(invalidCuisineIdMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, cuisineExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_ONE_OR_MORE_IDS))
    }

    @Test
    fun `should throw exception when add meal with empty description`() {
        // given a meal with empty description
        val emptyDescriptionMeal = createValidMeal().copy(description = "")
        val mealExecutable = Executable { validateAddMeal(emptyDescriptionMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_DESCRIPTION))
    }

    @Test
    fun `should throw exception when add meal with description is more than 255 `() {
        // given a meal when description is more than 255
        val invalidDescriptionMeal = createValidMeal().copy(description = "A".repeat(256))
        val mealExecutable = Executable { validateAddMeal(invalidDescriptionMeal) }

        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_DESCRIPTION))
    }

    @Test
    fun `should pass when add meal with description is valid `() {
        // given a meal when description is 255
        val invalidDescriptionMeal = createValidMeal().copy(description = "A".repeat(255))
        val mealExecutable = Executable { validateAddMeal(invalidDescriptionMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should throw exception when add meal with invalid price`() {
        val invalidPriceMeal = createValidMeal().copy(price = -10.0)
        val mealExecutable = Executable { validateAddMeal(invalidPriceMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE))
    }

    @Test
    fun `should pass when add meal with valid price`() {
        // given a meal with valid price
        val validPriceMeal = createValidMeal().copy(price = 10.0)
        val mealExecutable = Executable { validateAddMeal(validPriceMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should pass when add meal with minimum valid price `() {
        // given a meal with minimum valid price
        val minimumPriceMeal = createValidMeal().copy(price = 1.0)
        val mealExecutable = Executable { validateAddMeal(minimumPriceMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should pass when add meal with maximum valid price `() {
        // given a meal with maximum valid price
        val maximumPriceMeal = createValidMeal().copy(price = 1000.0)
        val mealExecutable = Executable { validateAddMeal(maximumPriceMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should throw exception when add meal with invalid negative price`() {
        // given a meal with invalid negative price
        val invalidNegativePriceMeal = createValidMeal().copy(price = -10.0)
        val mealExecutable = Executable { validateAddMeal(invalidNegativePriceMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE))
    }

    @Test
    fun `should throw exception when add meal with invalid zero price `() {
        // given a meal with invalid zero price
        val invalidZeroPriceMeal = createValidMeal().copy(price = 0.0)
        val mealExecutable = Executable { validateAddMeal(invalidZeroPriceMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE))
    }

    @Test
    fun `should throw exception when add meal with invalid high price `() {
        // given a meal with invalid high price
        val invalidHighPriceMeal = createValidMeal().copy(price = 1500.0)
        val mealExecutable = Executable { validateAddMeal(invalidHighPriceMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE))
    }


    private fun createValidMeal(): MealDetails {
        return MealDetails(
            id = "",
            restaurantId = "64cc5fdd52F4136b92938f8c",
            name = "Delicious Meal",
            description = "A mouthwatering dish",
            price = 20.0,
            cuisines = listOf(Cuisine("64cc5fdd52F4136b92938f8c", "Italian"))
        )
    }
}