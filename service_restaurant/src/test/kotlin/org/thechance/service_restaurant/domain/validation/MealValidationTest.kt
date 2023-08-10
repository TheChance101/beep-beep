package org.thechance.service_restaurant.domain.validation

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.usecase.validation.MealValidation
import org.thechance.service_restaurant.domain.usecase.validation.Validation
import org.thechance.service_restaurant.domain.usecase.validation.Validation.Companion.DESCRIPTION_MAX_LENGTH
import org.thechance.service_restaurant.domain.usecase.validation.Validation.Companion.DESCRIPTION_MIN_LENGTH
import org.thechance.service_restaurant.domain.usecase.validation.Validation.Companion.NULL_DOUBLE
import org.thechance.service_restaurant.domain.utils.*

class MealValidationTest {

    private val validationMeal = MealValidation(Validation())


    //region Add Meal Validation
    @Test
    fun `should pass validation when add meal with valid details`() {
        //given a valid meal
        val validMeal = createValidMeal()
        // No exception should be thrown
        val mealExecutable = Executable {
            validationMeal .validateAddMeal(validMeal)
        }
        //then no exception should be thrown and pass validation
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should throw exception when add meal with name is empty`() {
        // given a meal with name is empty
        val invalidNameMeal = createValidMeal().copy(name = " ")
        val nameExecutable = Executable { validationMeal.validateAddMeal(invalidNameMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when add meal with name is long`() {
        //given a meal with name is more than 25
        val invalidNameMeal = createValidMeal().copy(name = "A".repeat(26))
        val nameExecutable = Executable { validationMeal.validateAddMeal(invalidNameMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when add meal with name is short`() {
        // given a meal with name is less than four letter
        val invalidNameMeal = createValidMeal().copy(name = "A")
        val nameExecutable = Executable { validationMeal.validateAddMeal(invalidNameMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when add meal with invalid restaurant Id`() {
        val invalidRestaurantIdMeal = createValidMeal().copy(restaurantId = "invalid_id")
        val restaurantIdExecutable = Executable { validationMeal.validateAddMeal(invalidRestaurantIdMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, restaurantIdExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should throw exception when add meal with empty cuisines`() {
        // given a meal with empty cuisines
        val emptyCuisinesMeal = createValidMeal().copy(cuisines = emptyList())
        val cuisineExecutable = Executable {validationMeal. validateAddMeal(emptyCuisinesMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, cuisineExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_REQUEST_PARAMETER))
    }

    @Test
    fun `should throw exception when add meal with invalid cuisine Id`() {
        // given a meal with invalid cuisine Id
        val invalidCuisineIdMeal = createValidMeal().copy(cuisines = listOf(Cuisine("invalid_id", "Italian")))
        val cuisineExecutable = Executable { validationMeal.validateAddMeal(invalidCuisineIdMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, cuisineExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_ONE_OR_MORE_IDS))
    }

    @Test
    fun `should throw exception when add meal with empty description`() {
        // given a meal with empty description
        val emptyDescriptionMeal = createValidMeal().copy(description = "")
        val mealExecutable = Executable { validationMeal.validateAddMeal(emptyDescriptionMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_DESCRIPTION))
    }

    @Test
    fun `should throw exception when add meal with description is more than 255 `() {
        // given a meal when description is more than 255
        val invalidDescriptionMeal = createValidMeal().copy(description = "A".repeat(256))
        val mealExecutable = Executable { validationMeal.validateAddMeal(invalidDescriptionMeal) }

        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_DESCRIPTION))
    }

    @Test
    fun `should pass when add meal with description is valid `() {
        // given a meal when description is 255
        val invalidDescriptionMeal = createValidMeal().copy(description = "A".repeat(255))
        val mealExecutable = Executable {validationMeal. validateAddMeal(invalidDescriptionMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should throw exception when add meal with invalid price`() {
        val invalidPriceMeal = createValidMeal().copy(price = -10.0)
        val mealExecutable = Executable {validationMeal. validateAddMeal(invalidPriceMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE))
    }

    @Test
    fun `should pass when add meal with valid price`() {
        // given a meal with valid price
        val validPriceMeal = createValidMeal().copy(price = 10.0)
        val mealExecutable = Executable {validationMeal. validateAddMeal(validPriceMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should pass when add meal with minimum valid price `() {
        // given a meal with minimum valid price
        val minimumPriceMeal = createValidMeal().copy(price = 1.0)
        val mealExecutable = Executable { validationMeal.validateAddMeal(minimumPriceMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should pass when add meal with maximum valid price `() {
        // given a meal with maximum valid price
        val maximumPriceMeal = createValidMeal().copy(price = 1000.0)
        val mealExecutable = Executable {validationMeal. validateAddMeal(maximumPriceMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should throw exception when add meal with invalid negative price`() {
        // given a meal with invalid negative price
        val invalidNegativePriceMeal = createValidMeal().copy(price = -10.0)
        val mealExecutable = Executable { validationMeal.validateAddMeal(invalidNegativePriceMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE))
    }

    @Test
    fun `should throw exception when add meal with invalid zero price `() {
        // given a meal with invalid zero price
        val invalidZeroPriceMeal = createValidMeal().copy(price = 0.0)
        val mealExecutable = Executable {validationMeal. validateAddMeal(invalidZeroPriceMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE))
    }

    @Test
    fun `should throw exception when add meal with invalid high price `() {
        // given a meal with invalid high price
        val invalidHighPriceMeal = createValidMeal().copy(price = 1500.0)
        val mealExecutable = Executable {validationMeal. validateAddMeal(invalidHighPriceMeal) }
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

    // endregion

    // region Update Meal
    @Test
    fun `should throw MultiErrorException contains INVALID_REQUEST_PARAMETER when id is empty`() {
        // given empty id
        val id = ""
        val mealDetails = MealDetails(
            id = id,
            name = "name",
            description = "description",
            price = 10.0,
            restaurantId = "2a1b3c4d5e6f7a8b9c0d1e2f",
            cuisines = listOf(),
        )
        // when validateUpdateMeal is invoked
        val result = Executable { validationMeal.validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_REQUEST_PARAMETER
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_REQUEST_PARAMETER))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_REQUEST_PARAMETER when restaurantId is empty`() {
        // given empty restaurantId
        val restaurantId = ""
        val mealDetails = MealDetails(
            id = "2a1b3c4d5e6f7a8b9c0d1e2f",
            name = "name",
            description = "description",
            price = 10.0,
            restaurantId = restaurantId,
            cuisines = listOf(),
        )
        // when validateUpdateMeal is invoked
        val result = Executable {validationMeal. validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_REQUEST_PARAMETER
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_REQUEST_PARAMETER))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_ID when id is invalid`() {
        // given invalid id
        val id = "invalidId"
        val mealDetails = MealDetails(
            id = id,
            name = "name",
            description = "description",
            price = 10.0,
            restaurantId = "2a1b3c4d5e6f7a8b9c0d1e2f",
            cuisines = listOf(),
        )
        // when validateUpdateMeal is invoked
        val result = Executable { validationMeal.validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_ID
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_ID when restaurantId is invalid`() {
        // given invalid restaurantId
        val restaurantId = "invalidId"
        val mealDetails = MealDetails(
            id = "2a1b3c4d5e6f7a8b9c0d1e2f",
            name = "name",
            description = "description",
            price = 10.0,
            restaurantId = restaurantId,
            cuisines = listOf(),
        )
        // when validateUpdateMeal is invoked
        val result = Executable {validationMeal. validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_ID
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_UPDATE_PARAMETER when no updated fields`() {
        // given no updated fields
        val mealDetails = MealDetails(
            id = "2a1b3c4d5e6f7a8b9c0d1e2f",
            name = "",
            description = "",
            price = NULL_DOUBLE,
            restaurantId = "2a1b3c4d5e6f7a8b9c0d1e2f",
            cuisines = emptyList(),
        )
        // when validateUpdateMeal is invoked
        val result = Executable { validationMeal.validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_UPDATE_PARAMETER
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_UPDATE_PARAMETER))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_NAME when name is invalid`() {
        // given invalid name
        val name = "\$invalid @*Name"
        val mealDetails = MealDetails(
            id = "2a1b3c4d5e6f7a8b9c0d1e2f",
            name = name,
            description = "description",
            price = 10.0,
            restaurantId = "2a1b3c4d5e6f7a8b9c0d1e2f",
            cuisines = listOf(),
        )
        // when validateUpdateMeal is invoked
        val result = Executable {validationMeal. validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_NAME
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_DESCRIPTION when description length is grater than DESCRIPTION_MAX_LENGTH`() {
        // given description length is grater than DESCRIPTION_MAX_LENGTH
        val description = "d".repeat(DESCRIPTION_MAX_LENGTH + 1)
        val mealDetails = MealDetails(
            id = "2a1b3c4d5e6f7a8b9c0d1e2f",
            name = "meal name",
            description = description,
            price = 10.0,
            restaurantId = "2a1b3c4d5e6f7a8b9c0d1e2f",
            cuisines = listOf(),
        )
        // when validateUpdateMeal is invoked
        val result = Executable { validationMeal.validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_DESCRIPTION
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_DESCRIPTION))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_PRICE when price is out of price range`() {
        // given price is out of price range
        val price = 0.0
        val mealDetails = MealDetails(
            id = "2a1b3c4d5e6f7a8b9c0d1e2f",
            name = "meal name",
            description = "description",
            price = price,
            restaurantId = "2a1b3c4d5e6f7a8b9c0d1e2f",
            cuisines = listOf(),
        )
        // when validateUpdateMeal is invoked
        val result = Executable { validationMeal.validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_PRICE
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_PRICE))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_ID when one of cuisines ids is invalid`() {
        // given one of cuisines ids is invalid
        val cuisines = listOf(
            Cuisine(
                "2a1b3c4d5e6f7a8b9c0d1e2f",
                "cuisine name",
            ),
            Cuisine(
                "invalidId",
                "cuisine name",
            ),
        )
        val mealDetails = MealDetails(
            id = "2a1b3c4d5e6f7a8b9c0d1e2f",
            name = "meal name",
            description = "description",
            price = 10.0,
            restaurantId = "2a1b3c4d5e6f7a8b9c0d1e2f",
            cuisines = cuisines,
        )
        // when  validateUpdateMeal is invoked
        val result = Executable { validationMeal.validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_ID
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_ONE_OR_MORE_IDS when one of cuisines ids is invalid`() {
        // given one of cuisines ids is invalid
        val cuisines = listOf(
            Cuisine(
                "2a1b3c4d5e6f7a8b9c0d1e2f",
                "cuisine name",
            ),
            Cuisine(
                "invalidId",
                "cuisine name",
            ),
        )
        val mealDetails = MealDetails(
            id = "2a1b3c4d5e6f7a8b9c0d1e2f",
            name = "meal name",
            description = "description",
            price = 10.0,
            restaurantId = "2a1b3c4d5e6f7a8b9c0d1e2f",
            cuisines = cuisines,
        )
        // when  validateUpdateMeal is invoked
        val result = Executable {validationMeal. validateUpdateMeal(mealDetails) }
        // then check throw MultiErrorException contains INVALID_ONE_OR_MORE_IDS
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_ONE_OR_MORE_IDS))
    }

    @Test
    fun `should do nothing when meal details is valid`() {
        // given meal details is valid
        val cuisines = listOf(
            Cuisine(
                "2a1b3c4d5e6f7a8b9c0d1e2f",
                "cuisine name",
            ),
            Cuisine(
                "2a1b3c4d5e6f7a8b9c0d1e2f",
                "cuisine name",
            ),
        )
        val mealDetails = MealDetails(
            id = "2a1b3c4d5e6f7a8b9c0d1e2f",
            name = "meal name",
            description = "d".repeat(DESCRIPTION_MIN_LENGTH),
            price = 10.0,
            restaurantId = "2a1b3c4d5e6f7a8b9c0d1e2f",
            cuisines = cuisines,
        )
        // when validateUpdateMeal is invoked
        val result = Executable {validationMeal. validateUpdateMeal(mealDetails) }
        // then check nothing happens
        assertDoesNotThrow(result)
    }
    // endregion
}