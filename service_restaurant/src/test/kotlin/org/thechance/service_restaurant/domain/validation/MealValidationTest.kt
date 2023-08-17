package org.thechance.service_restaurant.domain.validation

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.usecase.validation.MealValidationUseCase
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.Validation.Companion.DESCRIPTION_MAX_LENGTH
import org.thechance.service_restaurant.domain.utils.Validation.Companion.DESCRIPTION_MIN_LENGTH
import org.thechance.service_restaurant.domain.utils.Validation.Companion.MAX_CUISINE
import org.thechance.service_restaurant.domain.utils.Validation.Companion.NULL_DOUBLE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_CUISINE_LIMIT
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_DESCRIPTION
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ONE_OR_MORE_IDS
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PRICE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_UPDATE_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

class MealValidationTest {

   private val mealValidation = MealValidationUseCase(Validation())


    //region Add Meal Validation
    @Test
    fun `should pass validation when add meal with valid details`() {
        //given a valid meal
        val validMeal = createValidMeal()
        // No exception should be thrown
        val mealExecutable = Executable {
            mealValidation.validateAddMeal(validMeal)
        }
        //then no exception should be thrown and pass validation
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should throw exception when add meal with name is empty`() {
        // given a meal with name is empty
        val invalidNameMeal = createValidMeal().copy(name = " ")
        val nameExecutable = Executable { mealValidation.validateAddMeal(invalidNameMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when add meal with name is long`() {
        //given a meal with name is more than 25
        val invalidNameMeal = createValidMeal().copy(name = "A".repeat(51))
        val nameExecutable = Executable { mealValidation.validateAddMeal(invalidNameMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when add meal with name is short`() {
        // given a meal with name is less than four letter
        val invalidNameMeal = createValidMeal().copy(name = "A")
        val nameExecutable = Executable { mealValidation.validateAddMeal(invalidNameMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when add meal with invalid restaurant Id`() {
        val invalidRestaurantIdMeal = createValidMeal().copy(restaurantId = "invalid_id")
        val restaurantIdExecutable =
            Executable { mealValidation.validateAddMeal(invalidRestaurantIdMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, restaurantIdExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should throw exception when add meal with empty cuisines`() {
        // given a meal with empty cuisines
        val emptyCuisinesMeal = createValidMeal().copy(cuisines = emptyList())
        val cuisineExecutable = Executable { mealValidation.validateAddMeal(emptyCuisinesMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, cuisineExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_REQUEST_PARAMETER))
    }

    @Test
    fun `should throw exception when add meal with more than MAX cuisines`() {
        // given a meal with more than max cuisines
        val cuisines = mutableListOf<Cuisine>()
        for (i in 0..(MAX_CUISINE + 1)) {
            cuisines.add(Cuisine("64cc5fdd52F4136b92938f8c", "Italian"))
        }
        val emptyCuisinesMeal = createValidMeal().copy(cuisines = cuisines)
        val cuisineExecutable = Executable { mealValidation.validateAddMeal(emptyCuisinesMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, cuisineExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_CUISINE_LIMIT))
    }

    @Test
    fun `should throw exception when add meal with invalid cuisine Id`() {
        // given a meal with invalid cuisine Id
        val invalidCuisineIdMeal =
            createValidMeal().copy(cuisines = listOf(Cuisine("invalid_id", "Italian")))
        val cuisineExecutable = Executable { mealValidation.validateAddMeal(invalidCuisineIdMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, cuisineExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_ONE_OR_MORE_IDS))
    }

    @Test
    fun `should throw exception when add meal with empty description`() {
        // given a meal with empty description
        val emptyDescriptionMeal = createValidMeal().copy(description = "")
        val mealExecutable = Executable { mealValidation.validateAddMeal(emptyDescriptionMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_DESCRIPTION))
    }

    @Test
    fun `should throw exception when add meal with description is more than 255 `() {
        // given a meal when description is more than 255
        val invalidDescriptionMeal = createValidMeal().copy(description = "A".repeat(256))
        val mealExecutable = Executable { mealValidation.validateAddMeal(invalidDescriptionMeal) }

        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_DESCRIPTION))
    }

    @Test
    fun `should pass when add meal with description is valid `() {
        // given a meal when description is 255
        val invalidDescriptionMeal = createValidMeal().copy(description = "A".repeat(255))
        val mealExecutable = Executable { mealValidation.validateAddMeal(invalidDescriptionMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should throw exception when add meal with invalid price`() {
        val invalidPriceMeal = createValidMeal().copy(price = -10.0)
        val mealExecutable = Executable { mealValidation.validateAddMeal(invalidPriceMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE))
    }

    @Test
    fun `should pass when add meal with valid price`() {
        // given a meal with valid price
        val validPriceMeal = createValidMeal().copy(price = 10.0)
        val mealExecutable = Executable { mealValidation.validateAddMeal(validPriceMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should pass when add meal with minimum valid price `() {
        // given a meal with minimum valid price
        val minimumPriceMeal = createValidMeal().copy(price = 1.0)
        val mealExecutable = Executable { mealValidation.validateAddMeal(minimumPriceMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should pass when add meal with maximum valid price `() {
        // given a meal with maximum valid price
        val maximumPriceMeal = createValidMeal().copy(price = 1000.0)
        val mealExecutable = Executable { mealValidation.validateAddMeal(maximumPriceMeal) }

        // then check if throw exception
        assertDoesNotThrow(mealExecutable)
    }

    @Test
    fun `should throw exception when add meal with invalid negative price`() {
        // given a meal with invalid negative price
        val invalidNegativePriceMeal = createValidMeal().copy(price = -10.0)
        val mealExecutable = Executable { mealValidation.validateAddMeal(invalidNegativePriceMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE))
    }

    @Test
    fun `should throw exception when add meal with invalid zero price `() {
        // given a meal with invalid zero price
        val invalidZeroPriceMeal = createValidMeal().copy(price = 0.0)
        val mealExecutable = Executable { mealValidation.validateAddMeal(invalidZeroPriceMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE))
    }

    @Test
    fun `should throw exception when add meal with invalid high price `() {
        // given a meal with invalid high price
        val invalidHighPriceMeal = createValidMeal().copy(price = 1500.0)
        val mealExecutable = Executable { mealValidation.validateAddMeal(invalidHighPriceMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, mealExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE))
    }


    private fun createValidMeal(): MealDetails {
        return MealDetails(
            id = "",
            restaurantId = "3edf2fc8-6983-484f-a35c-8190f44a08c6",
            name = "Delicious Meal",
            description = "A mouthwatering dish",
            price = 20.0,
            cuisines = listOf(Cuisine("3edf2fc8-6983-484f-a35c-8190f44a08c6", "Italian"))
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
        val result = Executable { mealValidation.validateUpdateMeal(mealDetails) }
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
        val result = Executable { mealValidation.validateUpdateMeal(mealDetails) }
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
        val result = Executable { mealValidation.validateUpdateMeal(mealDetails) }
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
        val result = Executable { mealValidation.validateUpdateMeal(mealDetails) }
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
        val result = Executable { mealValidation.validateUpdateMeal(mealDetails) }
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
        val result = Executable { mealValidation.validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_NAME
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when update meal with more than MAX cuisines`() {
        // given a meal with more than max cuisines
        val cuisines = mutableListOf<Cuisine>()
        for (i in 0..(MAX_CUISINE + 1)) {
            cuisines.add(Cuisine("64cc5fdd52F4136b92938f8c", "Italian"))
        }
        val emptyCuisinesMeal = createValidMeal().copy(cuisines = cuisines)
        val cuisineExecutable = Executable { mealValidation.validateUpdateMeal(emptyCuisinesMeal) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, cuisineExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_CUISINE_LIMIT))
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
        val result = Executable { mealValidation.validateUpdateMeal(mealDetails) }
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
        val result = Executable { mealValidation.validateUpdateMeal(mealDetails) }
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
            description = "d".repeat(100),
            price = 10.0,
            restaurantId = "2a1b3c4d5e6f7a8b9c0d1e2f",
            cuisines = cuisines,
        )
        // when  validateUpdateMeal is invoked
        val result = Executable { mealValidation.validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_ID
        val throwable = assertThrows(MultiErrorException::class.java, result)
       assertTrue(throwable.errorCodes.contains(INVALID_ONE_OR_MORE_IDS))
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
        val result = Executable { mealValidation.validateUpdateMeal(mealDetails) }
        // then check throw MultiErrorException contains INVALID_ONE_OR_MORE_IDS
        val throwable = assertThrows(MultiErrorException::class.java, result)
        assertTrue(throwable.errorCodes.contains(INVALID_ONE_OR_MORE_IDS))
    }

    @Test
    fun `should do nothing when meal details is valid`() {
        // given meal details is valid
        val cuisines = listOf(
            Cuisine(
                "3edf2fc8-6983-484f-a35c-8190f44a08c6",
                "cuisine name",
            ),
            Cuisine(
                "3edf2fc8-6983-484f-a35c-8190f44a08c6",
                "cuisine name",
            ),
        )
        val mealDetails = MealDetails(
            id = "3edf2fc8-6983-484f-a35c-8190f44a08c6",
            name = "meal name",
            description = "d".repeat(DESCRIPTION_MIN_LENGTH),
            price = 10.0,
            restaurantId = "3edf2fc8-6983-484f-a35c-8190f44a08c6",
            cuisines = cuisines,
        )
        // when validateUpdateMeal is invoked
        val result = Executable { mealValidation.validateUpdateMeal(mealDetails) }
        // then check nothing happens
        assertDoesNotThrow(result)
    }
    // endregion

}