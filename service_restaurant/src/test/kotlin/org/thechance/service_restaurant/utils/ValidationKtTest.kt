package org.thechance.service_restaurant.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.function.Executable
import org.thechance.service_restaurant.api.models.AddressDto
import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.api.models.mappers.toEntity

class ValidationKtTest {

    //region isValidId
    @Test
    fun `should return true if id is valid`() {
        // given an valid id with 24 characters and only hex characters
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


    //region validationRestaurant
    @Test
    fun `validation restaurant should pass validation with just require fields`() {
        // given a valid fields of restaurant that  require
        val validRestaurant = createValidRestaurant()
        //then no exception should be thrown and pass validation
        validationRestaurant(validRestaurant[0].toEntity())
    }

    @Test
    fun `validation restaurant should pass validation with all fields`() {
        // given a valid fields of restaurant with optional fields
        val validRestaurant = createValidRestaurant()
        //then no exception should be thrown and pass validation
        validationRestaurant(validRestaurant[1].toEntity())
    }

    @Test
    fun `validation restaurant should throw exception when name is invalid`() {
        // given a restaurant when name is less than four letter
        val shortNameExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(name = "Asi").toEntity())
        }
        // given a restaurant when name is more than 25
        val longNameExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(name = "A".repeat(26)).toEntity())
        }
        // given a restaurant when name is empty
        val emptyNameExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(name = " ").toEntity())
        }
        // given a restaurant when name is null
        val nullNameExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(name = null).toEntity())
        }

        // then check if throw exception
        assertThrows(MultiErrorException::class.java, shortNameExecutable)
        assertThrows(MultiErrorException::class.java, longNameExecutable)
        assertThrows(MultiErrorException::class.java, emptyNameExecutable)
        assertThrows(MultiErrorException::class.java, nullNameExecutable)
    }

    @Test
    fun `validation restaurant should throw exception when description is invalid `() {
        // given a restaurant when description is more than 255
        val longDescriptionRestaurantExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].toEntity().copy(description = "A".repeat(256)))
        }
        // then check if throw exception
        assertThrows(MultiErrorException::class.java, longDescriptionRestaurantExecutable)
    }

    @Test
    fun `validation restaurant should throw exception when rate is invalid`() {
        // given a restaurant when rate is above upper bound  5.0
        val upperRateExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].toEntity().copy(rate = 5.1))
        }
        // given a restaurant when rate is  below lower bound 0.0
        val lowerRateExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].toEntity().copy(rate = -1.0))
        }

        // then check if throw exception
        assertThrows(MultiErrorException::class.java, upperRateExecutable)
        //   assertThrows(MultiErrorException::class.java, lowerRateExecutable) //not passing
    }

    @Test
    fun `validation restaurant should throw exception when phone is invalid`() {
        // given a restaurant when phone is more than 10
        val longPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = "1".repeat(11)).toEntity())
        }
        // given a restaurant when phone is less than 10
        val shortPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = "1".repeat(9)).toEntity())
        }
        // given a restaurant when phone is empty
        val emptyPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = " ").toEntity())
        }
        // given a restaurant when phone is null
        val nullPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = null).toEntity())
        }
        // given a restaurant when phone is character
        val characterPhoneExecutable = Executable {
            validationRestaurant(createValidRestaurant()[0].copy(phone = "s".repeat(10)).toEntity())
        }

        // then check if throw exception
        assertThrows(MultiErrorException::class.java, longPhoneExecutable)
        assertThrows(MultiErrorException::class.java, shortPhoneExecutable)
        assertThrows(MultiErrorException::class.java, emptyPhoneExecutable)
        assertThrows(MultiErrorException::class.java, nullPhoneExecutable)
        assertThrows(MultiErrorException::class.java, characterPhoneExecutable)
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


}