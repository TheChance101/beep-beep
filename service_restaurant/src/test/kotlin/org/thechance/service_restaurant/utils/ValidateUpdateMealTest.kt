package org.thechance.service_restaurant.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.utils.DESCRIPTION_MAX_LENGTH
import org.thechance.service_restaurant.domain.utils.INVALID_DESCRIPTION
import org.thechance.service_restaurant.domain.utils.INVALID_ID
import org.thechance.service_restaurant.domain.utils.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.INVALID_ONE_OR_MORE_IDS
import org.thechance.service_restaurant.domain.utils.INVALID_PRICE
import org.thechance.service_restaurant.domain.utils.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.INVALID_UPDATE_PARAMETER
import org.thechance.service_restaurant.domain.utils.MultiErrorException
import org.thechance.service_restaurant.domain.utils.NULL_DOUBLE
import org.thechance.service_restaurant.domain.utils.validateUpdateMeal

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidateUpdateMealTest {

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
        val result = Executable { validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_REQUEST_PARAMETER
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_REQUEST_PARAMETER))
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
        val result = Executable { validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_REQUEST_PARAMETER
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_REQUEST_PARAMETER))
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
        val result = Executable { validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_ID
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_ID))
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
        val result = Executable { validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_ID
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_ID))
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
        val result = Executable { validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_UPDATE_PARAMETER
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_UPDATE_PARAMETER))
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
        val result = Executable { validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_NAME
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_NAME))
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
        val result = Executable { validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_DESCRIPTION
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_DESCRIPTION))
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
        val result = Executable { validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_PRICE
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_PRICE))
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
        val result = Executable { validateUpdateMeal(mealDetails) }
        // then throw MultiErrorException contains INVALID_ID
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_ID))
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
        val result = Executable { validateUpdateMeal(mealDetails) }
        // then check throw MultiErrorException contains INVALID_ONE_OR_MORE_IDS
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_ONE_OR_MORE_IDS))
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
            description = "description",
            price = 10.0,
            restaurantId = "2a1b3c4d5e6f7a8b9c0d1e2f",
            cuisines = cuisines,
        )
        // when validateUpdateMeal is invoked
        val result = Executable { validateUpdateMeal(mealDetails) }
        // then check nothing happens
        Assertions.assertDoesNotThrow(result)
    }
}