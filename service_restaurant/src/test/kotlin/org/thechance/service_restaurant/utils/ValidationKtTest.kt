package org.thechance.service_restaurant.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable
import org.thechance.service_restaurant.domain.entity.Address
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.entity.Restaurant

class RestaurantValidationTest {

    //region creat validationRestaurant
    @Test
    fun `should pass validation when creat restaurant with just require fields that valid`() {
        // given a valid fields of restaurant that  require
        val restaurantExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0])
        }
        //then no exception should be thrown and pass validation
        assertDoesNotThrow(restaurantExecutable)

    }

    @Test
    fun `should pass validation when creat restaurant with blank price level`() {
        // given a restaurant when price level is blank
        val blankPrice = createValidRestaurant().first().copy(priceLevel = "")
        // then no exception should be thrown
        validationRestaurant(blankPrice)
    }

    @Test
    fun `should pass validation when creat restaurant with valid price level`() {
        // given a restaurant when price level is blank
        val blankPrice = createValidRestaurant().first().copy(priceLevel = "$")
        // then no exception should be thrown
        validationRestaurant(blankPrice)
    }

    @Test
    fun `should pass validation when creat restaurant with all fields that valid`() {
        // given a valid fields of restaurant with optional fields
        val restaurantExecutable = Executable {
            validationRestaurant(createValidRestaurant()[1])
        }
        //then no exception should be thrown and pass validation
        assertDoesNotThrow(restaurantExecutable)
    }

    @Test
    fun `should throw exception when creat restaurant with name is empty`() {
        // given a restaurant when name is empty
        val emptyNameExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(name = " "))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, emptyNameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when creat restaurant with name is long`() {
        // given a restaurant when name is more than 25
        val longNameExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(name = "A".repeat(26)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, longNameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when creat restaurant with name is short`() {
        // given a restaurant when name is less than four letter
        val shortNameExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(name = "Asi"))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, shortNameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when creat restaurant with description is invalid `() {
        // given a restaurant when description is more than 255
        val longDescriptionExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(description = "A".repeat(256)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, longDescriptionExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_DESCRIPTION))
    }

    @Test
    fun `should pass when creat restaurant with description is valid `() {
        // given a restaurant when description is 255
        val longDescriptionExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(description = "A".repeat(255)))
        }
        // then check if throw exception
        assertDoesNotThrow(longDescriptionExecutable)
    }

    @Test
    fun `should throw exception when creat restaurant with rate is more than five`() {
        // given a restaurant when rate is above upper bound  5.0
        val upperRateExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(rate = 5.1))
        }

        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, upperRateExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_RATE))

    }

    @Test
    fun `should throw exception when creat restaurant with rate is lower than zero`() {
        // given a restaurant when rate is  below lower bound 0.0
        val lowerRateExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(rate = -1.0))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, lowerRateExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_RATE))
    }

    @Test
    fun `should throw exception when creat restaurant with phone is invalid`() {
        // given a restaurant when phone is character
        val characterPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = "s".repeat(10)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, characterPhoneExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should pass when creat restaurant with phone is valid`() {
        // given a restaurant when phone is character
        val characterPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = "1".repeat(10)))
        }
        // then check if throw exception
        assertDoesNotThrow(characterPhoneExecutable)
    }

    @Test
    fun `should throw exception when creat restaurant with phone is short`() {
        // given a restaurant when phone is less than 10
        val shortPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = "1".repeat(9)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, shortPhoneExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception when creat restaurant with phone is long`() {
        // given a restaurant when phone is more than 10
        val longPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = "1".repeat(11)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, longPhoneExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception when creat restaurant with phone is empty`() {
        // given a restaurant when phone is empty
        val emptyPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = " "))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, emptyPhoneExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception when creat restaurant with latitude invalid`() {
        // given a restaurant when latitude is more than 90.0
        val invalidLatitudeExecutable = Executable {
            validationRestaurant(
                createValidRestaurant()[0].copy(
                    address = Address(
                        latitude = 95.0,
                        longitude = 200.0
                    )
                )
            )
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidLatitudeExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_LOCATION))
    }

    @Test
    fun `should throw exception when creat restaurant with longitude invalid`() {
        // given a restaurant when longitude is more than 180.0
        val invalidLongitudeExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(address = Address(40.0, -190.0)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidLongitudeExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_LOCATION))
    }

    @Test
    fun `should throw exception when creat restaurant with latitude and longitude invalid`() {
        val invalidLongitudeAndLatitudeExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(address = Address(-91.0, -181.0)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidLongitudeAndLatitudeExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_LOCATION))
    }


    @Test
    fun `should throw exception when creat restaurant with invalid price level`() {
        // given a restaurant when price level not is $, $$, $$$, $$$$
        val invalidPriceLevelExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(priceLevel = "invalid_level"))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidPriceLevelExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE_LEVEL))
    }

    private fun createValidRestaurant(): List<Restaurant> {
        return listOf(
            Restaurant(
                name = "Good Restaurant",
                ownerId = "64cc5fdd52c4136b92938f8c",
                address = Address(0.0, 0.0),
                phone = "1234561234",
                openingTime = "08:00",
                closingTime = "22:00",
                id = "",
            ),
            Restaurant(
                name = "Good Restaurant",
                ownerId = "64cc5fdd52c4136b92938f8c",
                address = Address(0.0, 0.0),
                phone = "1234561234",
                openingTime = "08:00",
                closingTime = "22:00",
                priceLevel = "$",
                rate = 4.5,
                description = "Good Restaurant",
                id = "",
            )
        )
    }
    //endregion


}

class RestaurantOwnershipValidationTest {

    @Test
    fun `should throw exception when restaurant not exists in database`() {
        // given a restaurant with a valid ownerId
        val ownerId = "64cc5fdd52c4136b92938f8c"
        //when there is no restaurant with that ownerId
        val invalidOwnerIdExecutable = Executable {
            validateRestaurantOwnership(null, ownerId)
        }
        // then throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidOwnerIdExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should pass validation when restaurant has valid ownership`() {
        // given a restaurant with a valid ownerId
        val validRestaurant = createValidRestaurant()
        val ownerId = "64cc5fdd52c4136b92938f8c"
        // when the restaurant has the same ownerId
        val restaurantExecutable = Executable {
            validateRestaurantOwnership(validRestaurant, ownerId)
        }
        //then no exception should be thrown and pass validation
        assertDoesNotThrow(restaurantExecutable)
    }

    @Test
    fun `should throw exception when restaurant has invalid ownership`() {
        // given a restaurant with a valid ownerId
        val validRestaurant = createValidRestaurant()
        val ownerId = "64cc5fdd52c4131b92938f8c"
        // when the restaurant has not same ownerId
        val restaurantExecutable = Executable {
            validateRestaurantOwnership(validRestaurant, ownerId)
        }
        // then throw exception
        val error = assertThrows(MultiErrorException::class.java, restaurantExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PROPERTY_RIGHTS))
    }

    private fun createValidRestaurant(): Restaurant {
        return Restaurant(
            name = "Good Restaurant",
            ownerId = "64cc5fdd52c4136b92938f8c",
            address = Address(0.0, 0.0),
            description = "A nice place to eat",
            priceLevel = "$$",
            rate = 4.5,
            phone = "123-456-7890",
            openingTime = "08:00",
            closingTime = "22:00",
            id = ""
        )
    }
}

class GeneralValidationTest {

    //region isValidId
    @Test
    fun `should return true if id is valid`() {
        // given a valid id with 24 characters and only hex characters
        val validId = "64cc5fdd52F4136b92938f8c"
        // when result is true
        val result = isValidId(validId)
        // then check
        assertEquals(true, result)
    }

    @Test
    fun `should return false if id is null or empty`() {
        // when result is null or empty
        val resultNull = isValidId(null)
        val resultEmpty = isValidId("")

        assertEquals(false, resultNull)
        assertEquals(false, resultEmpty)
    }

    @Test
    fun `should return false if id is too short or too long`() {
        // given an invalid id that
        val invalidIdToShort = "12345678901234567890123"  //less than 24 characters
        val invalidIdToLong = "123456789012345678f9012345" // more than 24 characters

        // when result is false
        val resultShortId = isValidId(invalidIdToShort)
        val resultLongId = isValidId(invalidIdToLong)
        // then check
        assertEquals(false, resultShortId)
        assertEquals(false, resultLongId)
    }

    @Test
    fun `should return false if id contains non-hex characters uppercase or lowercase `() {
        // given an invalid id that 'z' and 'Z' is not a valid hex character
        val invalidIdWithLowercase = "64cc5fdd52c4136b92938f8z"
        val invalidIdWithUppercase = "64cc5fdd52c4136b92938f8Z"
        // when result is false
        val resultLowercaseId = isValidId(invalidIdWithLowercase)
        val resultUppercaseId = isValidId(invalidIdWithUppercase)
        // then check
        assertEquals(false, resultLowercaseId)
        assertEquals(false, resultUppercaseId)
    }
    //endregion

    //region validate price
    @Test
    fun `should return true when price is valid`() {
        // given a price that between 1.0 and 1000.0
        val validPrice = 500.0
        val result = validatePrice(validPrice)
        // then return true
        assertEquals(true, result)
    }

    @Test
    fun `should return true when price is minimum`() {
        // given a price that minimum 1.0
        val minimumValidPrice = 1.0
        val result = validatePrice(minimumValidPrice)
        // then return true
        assertEquals(true, result)
    }

    @Test
    fun `should return true when price is maximum`() {
        // given a price that maximum 1000.0
        val maximumValidPrice = 1000.0
        val result = validatePrice(maximumValidPrice)
        // then return true
        assertEquals(true, result)
    }

    @Test
    fun `should return false when price below lower bound`() {
        // given a price that less than  1.0
        val invalidPrice = 0.5
        val result = validatePrice(invalidPrice)
        assertEquals(false, result)
    }

    @Test
    fun `should return false when price above upper bound `() {
        // given a price that more than 1000.0
        val invalidPrice = 1500.0
        val result = validatePrice(invalidPrice)
        assertEquals(false, result)
    }

    @Test
    fun `should return false when price is negative `() {
        // given a price that negative
        val negativePrice = -100.0
        val result = validatePrice(negativePrice)
        assertEquals(false, result)
    }
    //endregion

    //region validate latitude and longitude
    @Test
    fun `should return true when latitude is valid`() {
        // given a latitude that between -90.0 and 90.0
        val validLatitude = 35.0
        val result = validateLatitude(validLatitude)
        // then return true
        assertEquals(true, result)
    }

    @Test
    fun `should return false when latitude invalid`() {
        // given a latitude that more than -90.0
        val invalidLatitude = -100.0
        val result = validateLatitude(invalidLatitude)
        // then return false
        assertEquals(false, result)
    }

    @Test
    fun `should return true when longitude valid`() {
        // given a longitude that between -180.0 and 180.0
        val validLongitude = -120.0
        val result = validateLongitude(validLongitude)
        // then return true
        assertEquals(true, result)
    }

    @Test
    fun `should return false when longitude invalid`() {
        // given a longitude that more than -180.0
        val invalidLongitude = -190.0
        val result = validateLongitude(invalidLongitude)
        // then return false
        assertEquals(false, result)
    }

    @Test
    fun `should return true when valid location`() {
        // given a latitude and longitude that is valid
        val validLatitude = 25.0
        val validLongitude = -70.0
        val result = validateLocation(validLatitude, validLongitude)
        // then return true
        assertEquals(true, result)
    }

    @Test
    fun `should return false when location with invalid latitude`() {
        // given a latitude that is valid and a longitude that is invalid
        val invalidLatitude = -100.0
        val validLongitude = 120.0
        val result = validateLocation(invalidLatitude, validLongitude)
        // then return false
        assertEquals(false, result)
    }

    @Test
    fun `should return false when location with invalid longitude`() {
        // given a latitude that is valid  and a longitude that more than -180.0
        val validLatitude = 40.0
        val invalidLongitude = -190.0
        val result = validateLocation(validLatitude, invalidLongitude)
        // then return false
        assertEquals(false, result)
    }

    @Test
    fun `should return false when location with both invalid latitude and longitude`() {
        // given a latitude that more than 90.0 and a longitude that more than 180.0
        val invalidLatitude = 95.0
        val invalidLongitude = 200.0
        val result = validateLocation(invalidLatitude, invalidLongitude)
        // then return false
        assertEquals(false, result)
    }
    //endregion

}

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
        assertDoesNotThrow(mealExecutable)    }

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
