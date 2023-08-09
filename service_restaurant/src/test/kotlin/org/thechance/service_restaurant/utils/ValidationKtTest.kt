package org.thechance.service_restaurant.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

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



}