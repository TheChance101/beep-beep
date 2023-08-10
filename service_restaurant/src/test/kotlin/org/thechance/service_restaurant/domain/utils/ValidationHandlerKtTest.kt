package org.thechance.service_restaurant.domain.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.function.Executable

class ValidationHandlerKtTest {

    //region pagination
    @Test
    fun `valid pagination values`() {
        // given valid pagination values
        val page = 1
        val limit = 10
        // when validatePagination is invoked
        val validPaginationExecutable = Executable { validatePagination(page, limit) }
        // then no exception is thrown
        Assertions.assertDoesNotThrow(validPaginationExecutable)
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_PAGE when page value is invalid`() {
        // given valid pagination values
        val page = 0
        val limit = 10
        // when validatePagination is invoked
        val result = assertThrows<MultiErrorException> { validatePagination(page, limit) }
        // then throw MultiErrorException contains INVALID_PAGE
        Assertions.assertTrue(result.errorCodes.contains(INVALID_PAGE))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_PAGE_LIMIT when limit below valid range`() {
        // given valid pagination values
        val page = 1
        val limit = 4
        // when validatePagination is invoked
        val result = assertThrows<MultiErrorException> { validatePagination(page, limit) }
        // then throw MultiErrorException contains INVALID_PAGE_LIMIT
        Assertions.assertTrue(result.errorCodes.contains(INVALID_PAGE_LIMIT))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_PAGE_LIMIT when limit above valid range`() {
        // given valid pagination values
        val page = 1
        val limit = 31
        // when validatePagination is invoked
        val result = assertThrows<MultiErrorException> { validatePagination(page, limit) }
        // then throw MultiErrorException contains INVALID_PAGE_LIMIT
        Assertions.assertTrue(result.errorCodes.contains(INVALID_PAGE_LIMIT))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_PAGE_LIMIT, INVALID_PAGE when limit and page invalid`() {
        // given valid pagination values
        val page = -1
        val limit = 31
        // when validatePagination is invoked
        val result = assertThrows<MultiErrorException> { validatePagination(page, limit) }
        // then throw MultiErrorException contains INVALID_PAGE_LIMIT and INVALID_PAGE
        Assertions.assertTrue(
            result.errorCodes.containsAll(
                listOf(INVALID_PAGE_LIMIT, INVALID_PAGE)
            )
        )
    }
    //endregion

    //region phone
    @Test
    fun `should success when phone number is valid format`() {
        // given valid phone number
        val phone = "1234567890"
        // when isValidatePhone is invoked
        val result = isValidatePhone(phone)
        // then return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when phone number contains spaces`() {
        // given valid phone number
        val phone = "123 456 7890"
        // when isValidatePhone is invoked
        val result = isValidatePhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when phone number with dashes`() {
        // given valid phone number
        val phone = "123-456-7890"
        // when isValidatePhone is invoked
        val result = isValidatePhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when phone number with non-numeric characters`() {
        // given valid phone number
        val phone = "123abc456def"
        // when isValidatePhone is invoked
        val result = isValidatePhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when phone number too short`() {
        // given valid phone number
        val phone = "123456"
        // when isValidatePhone is invoked
        val result = isValidatePhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when phone number too long`() {
        // given valid phone number
        val phone = "12345678901234"
        // when isValidatePhone is invoked
        val result = isValidatePhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when empty phone number`() {
        // given valid phone number
        val phone = ""
        // when isValidatePhone is invoked
        val result = isValidatePhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when phone number is null`() {
        // given valid phone number
        val phone: String? = null
        // when isValidatePhone is invoked
        val result = isValidatePhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }
    //endregion

    //region ID
    @Test
    fun `should return true when valid ID`() {
        // given valid id
        val id = "64cef66bc6aa0b35318c2b26"
        // when isValidId is invoked
        val result = isValidId(id)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when ID has lowercase letters`() {
        // given valid id
        val id = "a1b2c3d4e5f6g7h8i9j0k1l"
        // when isValidId is invoked
        val result = isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when ID contains special characters`() {
        // given valid id
        val id = "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o%"
        // when isValidId is invoked
        val result = isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when ID too short`() {
        // given valid id
        val id = "a1b2c3d4e5f6g7h8i9j0k1l"
        // when isValidId is invoked
        val result = isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when ID too long`() {
        // given valid id
        val id = "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q"
        // when isValidId is invoked
        val result = isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when ID empty`() {
        // given valid id
        val id = ""
        // when isValidId is invoked
        val result = isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when ID null`() {
        // given valid id
        val id: String? = null
        // when isValidId is invoked
        val result = isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }
    //endregion

    //region ids

    @Test
    fun `should return true when all IDs list are valid`() {
        // given valid ids list
        val ids = listOf("64cef670c6aa0b35318c2b27", "64cef673c6aa0b35318c2b28")
        // when isValidIds is invoked
        val result = isValidIds(ids)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when not all IDs list valid`() {
        // given valid ids list
        val ids = listOf("a1b2c3d4e5f6g7h8i9j0k1l", "invalidId", "1234567890abcdef12345678")
        // when isValidIds is invoked
        val result = isValidIds(ids)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when IDs list is empty`() {
        // given valid ids list
        val ids = emptyList<String>()
        // when isValidIds is invoked
        val result = isValidIds(ids)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when IDs list is null`() {
        // given valid ids list
        val ids: List<String>? = null
        // when isValidIds is invoked
        val result = isValidIds(ids)
        // expected return false
        Assertions.assertFalse(result)
    }
    //endregion

    //region Price level
    @Test
    fun `should return true when price level is valid`() {
        // given priceLevel
        val priceLevel = "$$$"
        // when validatePriceLevel is invoked
        val result = isValidatePriceLevel(priceLevel)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when price level is empty`() {
        // given priceLevel
        val priceLevel = ""
        // when validatePriceLevel is invoked
        val result = isValidatePriceLevel(priceLevel)
        // expected return true
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when price level is null`() {
        // given priceLevel
        val priceLevel: String? = null
        // when validatePriceLevel is invoked
        val result = isValidatePriceLevel(priceLevel)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when price level is not valid`() {
        // given priceLevel
        val priceLevel = "invalid"
        // when validatePriceLevel is invoked
        val result = isValidatePriceLevel(priceLevel)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when price level long string`() {
        // given priceLevel
        val priceLevel = "$$$$$"
        // when validatePriceLevel is invoked
        val result = isValidatePriceLevel(priceLevel)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when price level contains special char than $`() {
        // given priceLevel
        val priceLevel = "@@@"
        // when validatePriceLevel is invoked
        val result = isValidatePriceLevel(priceLevel)
        // expected return false
        Assertions.assertFalse(result)
    }

    //endregion

    //region price
    @Test
    fun `should return true when rate within range`() {
        // given rate
        val rate = 3.5
        // when validateRate is invoked
        val result = validateRate(rate)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return true when rate at lower bound`() {
        // given rate
        val rate = 0.0
        // when validateRate is invoked
        val result = validateRate(rate)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return true when rate at upper bound`() {
        // given rate
        val rate = 5.0
        // when validateRate is invoked
        val result = validateRate(rate)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when rate below lower bound`() {
        // given rate
        val rate = -1.0
        // when validateRate is invoked
        val result = validateRate(rate)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when rate above upper bound`() {
        // given rate
        val rate = 6.0
        // when validateRate is invoked
        val result = validateRate(rate)
        // expected return false
        Assertions.assertFalse(result)
    }
    //endregion

    //region description

    @Test
    fun `should return true when description within length limit`() {
        // given description
        val description = "A valid description."
        // when validateDescription is invoked
        val result = validateDescription(description)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return true when description at lower length limit`() {
        // given description
        val description = "T".repeat(DESCRIPTION_MIN_LENGTH)
        // when validateDescription is invoked
        val result = validateDescription(description)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when description short length limit`() {
        // given description
        val description = "T".repeat(DESCRIPTION_MIN_LENGTH - 1)
        // when validateDescription is invoked
        val result = validateDescription(description)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return true when description at upper length limit`() {
        // given description
        val description = "Max length description."
        // when validateDescription is invoked
        val result = validateDescription(description)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when description below lower length limit`() {
        // given description
        val description = "Too short."
        // when validateDescription is invoked
        val result = validateDescription(description)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return true when description on max length limit`() {
        // given description
        val description = "T".repeat(DESCRIPTION_MAX_LENGTH)
        // when validateDescription is invoked
        val result = validateDescription(description)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when description above upper length limit`() {
        // given description
        val description = "T".repeat(DESCRIPTION_MAX_LENGTH) + "T"
        // when validateDescription is invoked
        val result = validateDescription(description)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when description is empty`() {
        // given description
        val description = ""
        // when validateDescription is invoked
        val result = validateDescription(description)
        // expected return false
        Assertions.assertFalse(result)
    }
    //endregion

    //region time
    @Test
    fun `should return true when time format is valid`() {
        // given time
        val time = "12:34"
        // when validateTime is invoked
        val result = validateTime(time)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when time format is invalid`() {
        // given time
        val time = "123:45"
        // when validateTime is invoked
        val result = validateTime(time)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return true when time is null`() {
        // given time
        val time: String? = null
        // when validateTime is invoked
        val result = validateTime(time)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return true when time is empty`() {
        // given time
        val time = ""
        // when validateTime is invoked
        val result = validateTime(time)
        // expected return false
        Assertions.assertFalse(result)
    }
    //endregion
}