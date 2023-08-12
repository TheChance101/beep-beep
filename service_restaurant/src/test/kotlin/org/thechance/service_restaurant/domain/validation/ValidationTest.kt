package org.thechance.service_restaurant.domain.validation

import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.function.Executable
import org.koin.core.context.GlobalContext
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.Validation.Companion.DESCRIPTION_MAX_LENGTH
import org.thechance.service_restaurant.domain.utils.Validation.Companion.DESCRIPTION_MIN_LENGTH
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PAGE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PAGE_LIMIT
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

class ValidationTest {

    private var validation= Validation()


    //region isValidId
    @Test
    fun `should return true if id is valid`() {
        // given a valid id with 24 characters and only hex characters
        val validId = "3edf2fc8-6983-484f-a35c-8190f44a08c6"
        // when result is true
        val result = validation.isValidId(validId)
        // then check
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `should return false if id is null or empty`() {
        // when result is null or empty
        val resultNull = validation.isValidId(null)
        val resultEmpty = validation.isValidId("")

        Assertions.assertEquals(false, resultNull)
        Assertions.assertEquals(false, resultEmpty)
    }

    @Test
    fun `should return false if id is too short or too long`() {
        // given an invalid id that
        val invalidIdToShort = "3edf2fc8-6983-484f-a35c-8f44a08c6"  //less than 24 characters
        val invalidIdToLong = "3edf2fc8-6983-484f-a35c-8190f4hfff4a08c6" // more than 24 characters

        // when result is false
        val resultShortId = validation.isValidId(invalidIdToShort)
        val resultLongId = validation.isValidId(invalidIdToLong)
        // then check
        Assertions.assertEquals(false, resultShortId)
        Assertions.assertEquals(false, resultLongId)
    }

    @Test
    fun `should return false if id contains non-hex characters uppercase or lowercase `() {
        // given an invalid id that 'z' and 'Z' is not a valid hex character
        val invalidIdWithLowercase = "3edf2fc8-6983-484f-a35c-8190f4q08c6"
        val invalidIdWithUppercase = "3edf2fc8-6983-484f-a35c-8190fQ4a08c6"
        // when result is false
        val resultLowercaseId = validation.isValidId(invalidIdWithLowercase)
        val resultUppercaseId = validation.isValidId(invalidIdWithUppercase)
        // then check
        Assertions.assertEquals(false, resultLowercaseId)
        Assertions.assertEquals(false, resultUppercaseId)
    }
    //endregion

    //region validate price
    @Test
    fun `should return true when price is valid`() {
        // given a price that between 1.0 and 1000.0
        val validPrice = 500.0
        val result = validation.isValidPrice(validPrice)
        // then return true
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `should return true when price is minimum`() {
        // given a price that minimum 1.0
        val minimumValidPrice = 1.0
        val result = validation.isValidPrice(minimumValidPrice)
        // then return true
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `should return true when price is maximum`() {
        // given a price that maximum 1000.0
        val maximumValidPrice = 1000.0
        val result = validation.isValidPrice(maximumValidPrice)
        // then return true
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `should return false when price below lower bound`() {
        // given a price that less than  1.0
        val invalidPrice = 0.5
        val result = validation.isValidPrice(invalidPrice)
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `should return false when price above upper bound `() {
        // given a price that more than 1000.0
        val invalidPrice = 1500.0
        val result = validation.isValidPrice(invalidPrice)
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `should return false when price is negative `() {
        // given a price that negative
        val negativePrice = -100.0
        val result = validation.isValidPrice(negativePrice)
        Assertions.assertEquals(false, result)
    }
    //endregion

    //region validate latitude and longitude
    @Test
    fun `should return true when latitude is valid`() {
        // given a latitude that between -90.0 and 90.0
        val validLatitude = 35.0
        val result = validation.isValidLatitude(validLatitude)
        // then return true
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `should return false when latitude invalid`() {
        // given a latitude that more than -90.0
        val invalidLatitude = -100.0
        val result = validation.isValidLatitude(invalidLatitude)
        // then return false
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `should return true when longitude valid`() {
        // given a longitude that between -180.0 and 180.0
        val validLongitude = -120.0
        val result = validation.isValidLongitude(validLongitude)
        // then return true
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `should return false when longitude invalid`() {
        // given a longitude that more than -180.0
        val invalidLongitude = -190.0
        val result = validation.isValidLongitude(invalidLongitude)
        // then return false
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `should return true when valid location`() {
        // given a latitude and longitude that is valid
        val validLatitude = 25.0
        val validLongitude = -70.0
        val result = validation.isValidLocation(validLatitude, validLongitude)
        // then return true
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `should return false when location with invalid latitude`() {
        // given a latitude that is valid and a longitude that is invalid
        val invalidLatitude = -100.0
        val validLongitude = 120.0
        val result = validation.isValidLocation(invalidLatitude, validLongitude)
        // then return false
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `should return false when location with invalid longitude`() {
        // given a latitude that is valid  and a longitude that more than -180.0
        val validLatitude = 40.0
        val invalidLongitude = -190.0
        val result = validation.isValidLocation(validLatitude, invalidLongitude)
        // then return false
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `should return false when location with both invalid latitude and longitude`() {
        // given a latitude that more than 90.0 and a longitude that more than 180.0
        val invalidLatitude = 95.0
        val invalidLongitude = 200.0
        val result = validation.isValidLocation(invalidLatitude, invalidLongitude)
        // then return false
        Assertions.assertEquals(false, result)
    }
    //endregion

    //region pagination
    @Test
    fun `valid pagination values`() {
        // given valid pagination values
        val page = 1
        val limit = 10
        // when validatePagination is invoked
        val validPaginationExecutable = Executable { validation.validatePagination(page, limit) }
        // then no exception is thrown
        Assertions.assertDoesNotThrow(validPaginationExecutable)
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_PAGE when page value is invalid`() {
        // given valid pagination values
        val page = 0
        val limit = 10
        // when validatePagination is invoked
        val result = assertThrows<MultiErrorException> { validation.validatePagination(page, limit) }
        // then throw MultiErrorException contains INVALID_PAGE
        Assertions.assertTrue(result.errorCodes.contains(INVALID_PAGE))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_PAGE_LIMIT when limit below valid range`() {
        // given valid pagination values
        val page = 1
        val limit = 4
        // when validatePagination is invoked
        val result = assertThrows<MultiErrorException> { validation.validatePagination(page, limit) }
        // then throw MultiErrorException contains INVALID_PAGE_LIMIT
        Assertions.assertTrue(result.errorCodes.contains(INVALID_PAGE_LIMIT))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_PAGE_LIMIT when limit above valid range`() {
        // given valid pagination values
        val page = 1
        val limit = 101
        // when validatePagination is invoked
        val result = assertThrows<MultiErrorException> { validation.validatePagination(page, limit) }
        // then throw MultiErrorException contains INVALID_PAGE_LIMIT
        Assertions.assertTrue(result.errorCodes.contains(INVALID_PAGE_LIMIT))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_PAGE_LIMIT, INVALID_PAGE when limit and page invalid`() {
        // given valid pagination values
        val page = -1
        val limit = 101
        // when validatePagination is invoked
        val result = assertThrows<MultiErrorException> { validation.validatePagination(page, limit) }
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
        val result = validation.isValidPhone(phone)
        // then return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when phone number contains spaces`() {
        // given valid phone number
        val phone = "123 456 7890"
        // when isValidatePhone is invoked
        val result = validation.isValidPhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when phone number with dashes`() {
        // given valid phone number
        val phone = "123-456-7890"
        // when isValidatePhone is invoked
        val result = validation.isValidPhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when phone number with non-numeric characters`() {
        // given valid phone number
        val phone = "123abc456def"
        // when isValidatePhone is invoked
        val result = validation.isValidPhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when phone number too short`() {
        // given valid phone number
        val phone = "123456"
        // when isValidatePhone is invoked
        val result = validation.isValidPhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when phone number too long`() {
        // given valid phone number
        val phone = "12345678901234"
        // when isValidatePhone is invoked
        val result = validation.isValidPhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when empty phone number`() {
        // given valid phone number
        val phone = ""
        // when isValidatePhone is invoked
        val result = validation.isValidPhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when phone number is null`() {
        // given valid phone number
        val phone: String? = null
        // when isValidatePhone is invoked
        val result = validation.isValidPhone(phone)
        // then return false
        Assertions.assertFalse(result)
    }
    //endregion

    //region ID
    @Test
    fun `should return true when valid ID`() {
        // given valid id
        val id = "3edf2fc8-6983-484f-a35c-8190f44a08c6"
        // when isValidId is invoked
        val result = validation.isValidId(id)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when ID has valid lowercase letters`() {
        // given valid id
        val id = "3edf2fc8-6983-484f-a35c-8190f44q08c6"
        // when isValidId is invoked
        val result = validation.isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when ID contains special characters`() {
        // given valid id
        val id = "3edf2fc8-6983-484f-a35c-8190q44a08q6%"
        // when isValidId is invoked
        val result = validation.isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when ID too short`() {
        // given valid id
        val id = "3edf2fc8-6983-484f-a35c-8190f4"
        // when isValidId is invoked
        val result = validation.isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when ID too long`() {
        // given valid id
        val id = "3edf2fc8-6983-484f-a35c-8190f44a08c6ffff"
        // when isValidId is invoked
        val result = validation.isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when ID empty`() {
        // given valid id
        val id = ""
        // when isValidId is invoked
        val result = validation.isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when ID null`() {
        // given valid id
        val id: String? = null
        // when isValidId is invoked
        val result = validation.isValidId(id)
        // expected return false
        Assertions.assertFalse(result)
    }
    //endregion

    //region ids

    @Test
    fun `should return true when all IDs list are valid`() {
        // given valid ids list
        val ids = listOf("3edf2fc8-6983-484f-a35c-8190f44a08c6", "3edf2fc8-6983-484f-a35c-8190f44a08c6")
        // when isValidIds is invoked
        val result = validation.isValidIds(ids)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when not all IDs list valid`() {
        // given valid ids list
        val ids = listOf("3edf2fc8-6983-484f-a35c-8190f44a08c6", "invalidId", "3edf2fc8-6983-484f-a35c-8190f44a08c6")
        // when isValidIds is invoked
        val result = validation.isValidIds(ids)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when IDs list is empty`() {
        // given valid ids list
        val ids = emptyList<String>()
        // when isValidIds is invoked
        val result = validation.isValidIds(ids)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when IDs list is null`() {
        // given valid ids list
        val ids: List<String>? = null
        // when isValidIds is invoked
        val result = validation.isValidIds(ids)
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
        val result = validation.isValidatePriceLevel(priceLevel)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when price level is empty`() {
        // given priceLevel
        val priceLevel = ""
        // when validatePriceLevel is invoked
        val result = validation.isValidatePriceLevel(priceLevel)
        // expected return true
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when price level is not valid`() {
        // given priceLevel
        val priceLevel = "invalid"
        // when validatePriceLevel is invoked
        val result = validation.isValidatePriceLevel(priceLevel)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when price level long string`() {
        // given priceLevel
        val priceLevel = "$$$$"
        // when validatePriceLevel is invoked
        val result = validation.isValidatePriceLevel(priceLevel)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when price level contains special char than $`() {
        // given priceLevel
        val priceLevel = "@@@"
        // when validatePriceLevel is invoked
        val result = validation.isValidatePriceLevel(priceLevel)
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
        val result = validation.isValidRate(rate)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return true when rate at lower bound`() {
        // given rate
        val rate = 0.0
        // when validateRate is invoked
        val result = validation.isValidRate(rate)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return true when rate at upper bound`() {
        // given rate
        val rate = 5.0
        // when validateRate is invoked
        val result = validation.isValidRate(rate)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when rate below lower bound`() {
        // given rate
        val rate = -1.0
        // when validateRate is invoked
        val result = validation.isValidRate(rate)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when rate above upper bound`() {
        // given rate
        val rate = 6.0
        // when validateRate is invoked
        val result = validation.isValidRate(rate)
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
        val result = validation.isValidDescription(description)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return true when description at lower length limit`() {
        // given description
        val description = "T".repeat(DESCRIPTION_MIN_LENGTH)
        // when validateDescription is invoked
        val result = validation.isValidDescription(description)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when description short length limit`() {
        // given description
        val description = "T".repeat(DESCRIPTION_MIN_LENGTH - 1)
        // when validateDescription is invoked
        val result = validation.isValidDescription(description)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return true when description at upper length limit`() {
        // given description
        val description = "Max length description."
        // when validateDescription is invoked
        val result = validation.isValidDescription(description)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when description below lower length limit`() {
        // given description
        val description = "Too short."
        // when validateDescription is invoked
        val result = validation.isValidDescription(description)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return true when description on max length limit`() {
        // given description
        val description = "T".repeat(DESCRIPTION_MAX_LENGTH)
        // when validateDescription is invoked
        val result = validation.isValidDescription(description)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when description above upper length limit`() {
        // given description
        val description = "T".repeat(DESCRIPTION_MAX_LENGTH) + "T"
        // when validateDescription is invoked
        val result = validation.isValidDescription(description)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return false when description is empty`() {
        // given description
        val description = ""
        // when validateDescription is invoked
        val result = validation.isValidDescription(description)
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
        val result = validation.isValidTime(time)
        // expected return true
        Assertions.assertTrue(result)
    }

    @Test
    fun `should return false when time format is invalid`() {
        // given time
        val time = "123:45"
        // when validateTime is invoked
        val result = validation.isValidTime(time)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return true when time is null`() {
        // given time
        val time: String? = null
        // when validateTime is invoked
        val result = validation.isValidTime(time)
        // expected return false
        Assertions.assertFalse(result)
    }

    @Test
    fun `should return true when time is empty`() {
        // given time
        val time = ""
        // when validateTime is invoked
        val result = validation.isValidTime(time)
        // expected return false
        Assertions.assertFalse(result)
    }
    //endregion

    //region validate ids
    @Test
    fun `should throw MultiErrorException contains INVALID_ID code when id is invalid`() {
        // given invalid id
        val id = "invalid id"
        // when checkIsValidIds is called
        val result = Executable { validation.checkIsValidIds(id, listOf("2a1b3c4d5e6f7a8b9c0d1e2f")) }
        // then check if MultiErrorException contains INVALID_ID code
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_ID code twice when id and listIds are invalid`() {
        // given invalid id and listIds
        val id = "invalid id"
        val listIds = listOf("invalid id", "2a1b3c4d5e6f7a8b9c0d1e2f")
        // when checkIsValidIds is called
        val result = Executable { validation.checkIsValidIds(id, listIds) }
        // then check if MultiErrorException contains INVALID_ID code twice
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.let { errorCodes -> errorCodes.all { it == INVALID_ID } && errorCodes.size == 2 })
    }

    @Test
    fun `should do nothing when ids are valid`() {
        // given valid id and listIds
        val id = "3edf2fc8-6983-484f-a35c-8190f44a08c6"
        val listIds = listOf("3edf2fc8-6983-484f-a35c-8190f44a08c6", "3edf2fc8-6983-484f-a35c-8190f44a08c6")
        // when checkIsValidIds is called
        val result = Executable { validation.checkIsValidIds(id, listIds) }
        // then check if result has no errors
        Assertions.assertDoesNotThrow(result)
    }
    //endregion
}