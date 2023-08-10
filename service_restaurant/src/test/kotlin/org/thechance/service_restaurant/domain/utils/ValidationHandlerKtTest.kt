package org.thechance.service_restaurant.domain.utils

import org.junit.Test
import org.junit.Assert.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.function.Executable

class ValidationHandlerKtTest {

    //region pagination
    @Test
    fun `valid pagination values`() {
        val page = 1
        val limit = 10

        // No exception should be thrown for valid pagination values
        try {
            validatePagination(page, limit)
        } catch (e: MultiErrorException) {
            fail("Unexpected exception: ${e.errorCodes}")
        }
    }

    @Test
    fun `invalid page value`() {
        val page = 0
        val limit = 10

        // MultiErrorException should be thrown due to invalid page value
        val result = Executable { validatePagination(page, limit) }
        // then check if throw exception
        val error = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(error.errorCodes.contains(INVALID_PAGE))
    }

    @Test
    fun `limit below valid range`() {
        val page = 1
        val limit = 4

        val result = assertThrows<MultiErrorException> {
            validatePagination(page, limit)
        }
        Assertions.assertTrue(result.errorCodes.contains(INVALID_PAGE_LIMIT))
    }

    @Test
    fun `limit above valid range`() {
        val page = 1
        val limit = 31

        val result = assertThrows<MultiErrorException> {
            validatePagination(page, limit)
        }
        Assertions.assertTrue(result.errorCodes.contains(INVALID_PAGE_LIMIT))
    }

    @Test
    fun `invalid limit and page`() {
        val page = -1
        val limit = 31

        val result = assertThrows<MultiErrorException> {
            validatePagination(page, limit)
        }
        Assertions.assertTrue(
            result.errorCodes.containsAll(
                listOf(INVALID_PAGE_LIMIT, INVALID_PAGE)
            )
        )
    }
    //endregion

    //region phone
    @Test
    fun `valid phone number`() {
        val phone = "1234567890"
        val result = isValidatePhone(phone)
        Assertions.assertTrue(result)
    }

    @Test
    fun `invalid phone number with spaces`() {
        val phone = "123 456 7890"
        val result = isValidatePhone(phone)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid phone number with dashes`() {
        val phone = "123-456-7890"
        val result = isValidatePhone(phone)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid phone number with non-numeric characters`() {
        val phone = "123abc456def"
        val result = isValidatePhone(phone)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid phone number too short`() {
        val phone = "123456"
        val result = isValidatePhone(phone)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid phone number too long`() {
        val phone = "12345678901234"
        val result = isValidatePhone(phone)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid empty phone number`() {
        val phone = ""
        val result = isValidatePhone(phone)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid null phone number`() {
        val phone: String? = null
        val result = isValidatePhone(phone)
        Assertions.assertFalse(result)
    }
    //endregion


    //region ID
    @Test
    fun `valid ID`() {
        val id = "64cef66bc6aa0b35318c2b26"
        val result = isValidId(id)
        Assertions.assertTrue(result)
    }

    @Test
    fun `invalid ID with lowercase letters`() {
        val id = "a1b2c3d4e5f6g7h8i9j0k1l"
        val result = isValidId(id)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid ID with special characters`() {
        val id = "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o%"
        val result = isValidId(id)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid ID too short`() {
        val id = "a1b2c3d4e5f6g7h8i9j0k1l"
        val result = isValidId(id)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid ID too long`() {
        val id = "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q"
        val result = isValidId(id)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid ID empty`() {
        val id = ""
        val result = isValidId(id)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid ID null`() {
        val id: String? = null
        val result = isValidId(id)
        Assertions.assertFalse(result)
    }
    //endregion

    //region ids


    @Test
    fun `valid IDs list`() {
        val ids = listOf("64cef670c6aa0b35318c2b27", "64cef673c6aa0b35318c2b28")
        val result = isValidIds(ids)
        Assertions.assertTrue(result)
    }

    @Test
    fun `invalid IDs list`() {
        val ids = listOf("a1b2c3d4e5f6g7h8i9j0k1l", "invalidId", "1234567890abcdef12345678")
        val result = isValidIds(ids)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid IDs empty`() {
        val ids = emptyList<String>()
        val result = isValidIds(ids)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid IDs null`() {
        val ids: List<String>? = null
        val result = isValidIds(ids)
        Assertions.assertFalse(result)
    }
    //endregion

    //region Price level
    @Test
    fun `valid price level`() {
        val priceLevel = "$$$"
        val result = validatePriceLevel(priceLevel)
        Assertions.assertTrue(result)
    }

    @Test
    fun `invalid price level empty`() {
        val priceLevel = ""
        val result = validatePriceLevel(priceLevel)
        Assertions.assertTrue(result)
    }

    @Test
    fun `invalid price level null`() {
        val priceLevel: String? = null
        val result = validatePriceLevel(priceLevel)
        Assertions.assertTrue(result)
    }

    @Test
    fun `invalid price level`() {
        val priceLevel = "invalid"
        val result = validatePriceLevel(priceLevel)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid price level long string`() {
        val priceLevel = "$$$$$"
        val result = validatePriceLevel(priceLevel)
        Assertions.assertFalse(result)
    }

    @Test
    fun `invalid price level special char`() {
        val priceLevel = "@@@"
        val result = validatePriceLevel(priceLevel)
        Assertions.assertFalse(result)
    }

    //endregion
}