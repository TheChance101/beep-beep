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

}