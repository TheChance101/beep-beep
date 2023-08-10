package org.thechance.service_restaurant.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions.*


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


