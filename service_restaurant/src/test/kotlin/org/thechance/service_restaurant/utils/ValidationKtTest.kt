package org.thechance.service_restaurant.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.function.Executable
import org.thechance.service_restaurant.api.models.AddressDto
import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.domain.utils.INVALID_DESCRIPTION
import org.thechance.service_restaurant.domain.utils.INVALID_LOCATION
import org.thechance.service_restaurant.domain.utils.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.INVALID_PHONE
import org.thechance.service_restaurant.domain.utils.INVALID_PRICE_LEVEL
import org.thechance.service_restaurant.domain.utils.INVALID_RATE
import org.thechance.service_restaurant.domain.utils.MultiErrorException
import org.thechance.service_restaurant.domain.utils.isValidId
import org.thechance.service_restaurant.domain.utils.validateLatitude
import org.thechance.service_restaurant.domain.utils.validateLocation
import org.thechance.service_restaurant.domain.utils.validateLongitude
import org.thechance.service_restaurant.domain.utils.validatePrice
import org.thechance.service_restaurant.domain.utils.validationRestaurant

class ValidationKtTest {

    //region creat validationRestaurant
    @Test
    fun `should pass validation when creat restaurant with just require fields that valid`() {
        // given a valid fields of restaurant that  require
        val validRestaurant = createValidRestaurant()
        //then no exception should be thrown and pass validation
        validationRestaurant(validRestaurant[0].toEntity())
    }


    @Test
    fun `should pass validation when creat restaurant with null price level`() {
        // given a restaurant when price level is null
        val nullPrice = createValidRestaurant().first().copy(priceLevel = null)
        // then no exception should be thrown
        validationRestaurant(nullPrice.toEntity())
    }

    @Test
    fun `should pass validation when creat restaurant with blank price level`() {
        // given a restaurant when price level is blank
        val blankPrice = createValidRestaurant().first().copy(priceLevel = "")
        // then no exception should be thrown
        validationRestaurant(blankPrice.toEntity())
    }

    @Test
    fun `should pass validation when creat restaurant with valid price level`() {
        // given a restaurant when price level is blank
        val blankPrice = createValidRestaurant().first().copy(priceLevel = "$")
        // then no exception should be thrown
        validationRestaurant(blankPrice.toEntity())
    }

    @Test
    fun `should pass validation when creat restaurant with all fields that valid`() {
        // given a valid fields of restaurant with optional fields
        val validRestaurant = createValidRestaurant()
        //then no exception should be thrown and pass validation
        validationRestaurant(validRestaurant[1].toEntity())
    }

    @Test
    fun `should throw exception when creat restaurant with name is empty`() {
        // given a restaurant when name is empty
        val emptyNameExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(name = " ").toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, emptyNameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when creat restaurant with name is null`() {
        // given a restaurant when name is null
        val nullNameExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(name = null).toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nullNameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when creat restaurant with name is long`() {
        // given a restaurant when name is more than 25
        val longNameExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(name = "A".repeat(26)).toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, longNameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when creat restaurant with name is short`() {
        // given a restaurant when name is less than four letter
        val shortNameExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(name = "Asi").toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, shortNameExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when creat restaurant with description is invalid `() {
        // given a restaurant when description is more than 255
        val longDescriptionExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].toEntity().copy(description = "A".repeat(256)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, longDescriptionExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_DESCRIPTION))
    }

    @Test
    fun `should throw exception when creat restaurant with rate is more than five`() {
        // given a restaurant when rate is above upper bound  5.0
        val upperRateExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].toEntity().copy(rate = 5.1))
        }

        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, upperRateExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_RATE))

    }

    @Test
    fun `should throw exception when creat restaurant with rate is lower than zero`() {
        // given a restaurant when rate is  below lower bound 0.0
        val lowerRateExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].toEntity().copy(rate = -1.0))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, lowerRateExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_RATE))
    }

    @Test
    fun `should throw exception when creat restaurant with phone is invalid`() {
        // given a restaurant when phone is character
        val characterPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = "s".repeat(10)).toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, characterPhoneExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception when creat restaurant with phone is short`() {
        // given a restaurant when phone is less than 10
        val shortPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = "1".repeat(9)).toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, shortPhoneExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception when creat restaurant with phone is long`() {
        // given a restaurant when phone is more than 10
        val longPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = "1".repeat(11)).toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, longPhoneExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception when creat restaurant with phone is null`() {
        // given a restaurant when phone is null
        val nullPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = null).toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nullPhoneExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception when creat restaurant with phone is empty`() {
        // given a restaurant when phone is empty
        val emptyPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = " ").toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, emptyPhoneExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception when creat restaurant with address null`() {
        // given a restaurant when address is null
        val nullAddressExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(address = null).toEntity())
        }

        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, nullAddressExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_LOCATION))
    }

    @Test
    fun `should throw exception when creat restaurant with latitude invalid`() {
        // given a restaurant when latitude is more than 90.0
        val invalidLatitudeExecutable = Executable {
            validationRestaurant(
                createValidRestaurant()[0].copy(
                    address = AddressDto(
                        latitude = 95.0,
                        longitude = 200.0
                    )
                ).toEntity()
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
            validationRestaurant(createValidRestaurant()[0].copy(address = AddressDto(40.0, -190.0)).toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidLongitudeExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_LOCATION))
    }

    @Test
    fun `should throw exception when creat restaurant with latitude and longitude invalid`() {
        val invalidLongitudeAndLatitudeExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(address = AddressDto(-91.0, -181.0)).toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidLongitudeAndLatitudeExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_LOCATION))
    }


    @Test
    fun `should throw exception when creat restaurant with invalid price level`() {
        // given a restaurant when price level not is $, $$, $$$, $$$$
        val invalidPriceLevelExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(priceLevel = "invalid_level").toEntity())
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidPriceLevelExecutable)
        assertEquals(true, error.errorCodes.contains(INVALID_PRICE_LEVEL))
    }

    private fun createValidRestaurant(): List<RestaurantDto> {
        return listOf(
            RestaurantDto(
                name = "Good Restaurant",
                ownerId = "64cc5fdd52c4136b92938f8c",
                address = AddressDto(0.0, 0.0),
                phone = "1234561234",
                openingTime = "08:00",
                closingTime = "22:00",
            ),
            RestaurantDto(
                name = "Good Restaurant",
                ownerId = "64cc5fdd52c4136b92938f8c",
                address = AddressDto(0.0, 0.0),
                phone = "1234561234",
                openingTime = "08:00",
                closingTime = "22:00",
                description = "hhg",
                priceLevel = "$",
                rate = 4.5,
            )
        )
    }
    //endregion

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
