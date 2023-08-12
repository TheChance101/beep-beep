package org.thechance.service_identity.domain.usecases.validation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AddressValidationUseCaseTest {

    private val addressValidationUseCase = AddressValidationUseCase()

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

}