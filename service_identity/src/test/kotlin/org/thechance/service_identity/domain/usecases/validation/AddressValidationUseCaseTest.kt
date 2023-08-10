package org.thechance.service_identity.domain.usecases.validation

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
class AddressValidationUseCaseTest {

    private val addressValidationUseCase = AddressValidationUseCase()

    @Test
    fun `should return true when user ID is not empty`() {
        val userId = "5f1d7e9d8456a60d887"
        val result = addressValidationUseCase.validateUserIdNotEmpty(userId)
        assertTrue(result) // Expecting the result to be true
    }

    @Test
    fun `should return false when user ID is empty`() {
        val userId = ""
        val result = addressValidationUseCase.validateUserIdNotEmpty(userId)
        assertFalse(result)
    }

    @Test
    fun `should return true when location is accepted`() {
        val validLatitude = 40.0
        val validLongitude = -75.0
        val result = addressValidationUseCase.validateLocation(validLatitude, validLongitude)
        assertTrue(result, "Valid location should be accepted")
    }

    @Test
    fun `should return false when latitude is rejected`() {
        val invalidLatitude = 100.0
        val validLongitude = -75.0
        val result = addressValidationUseCase.validateLocation(invalidLatitude, validLongitude)
        assertFalse(result, "Invalid latitude should be rejected")
    }

    @Test
    fun `should return false when longitude is rejected`() {
        val validLatitude = 40.0
        val invalidLongitude = -200.0
        val result = addressValidationUseCase.validateLocation(validLatitude, invalidLongitude)
        assertFalse(result, "Invalid longitude should be rejected")
    }

    @Test
    fun `should return true when user ID length is accepted`() {
        val validUserId = "5f1d7e9d8456a60d8877f8b2"
        val result = addressValidationUseCase.validateUserIdHexLength(validUserId)
        assertTrue(result, "Valid user ID length should be accepted")
    }

    @Test
    fun `should return false when user ID length is rejected`() {
        val invalidUserId = "5f1d7e9d8456a60d88"
        val result = addressValidationUseCase.validateUserIdHexLength(invalidUserId)
        assertFalse(result, "Invalid user ID length should be rejected")
    }
}