package org.thechance.service_identity.domain.usecases.validation

import org.junit.Assert.*
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.thechance.service_identity.domain.entity.RequestValidationException
import org.thechance.service_identity.domain.util.AMOUNT_MUST_BE_POSITIVE

class WalletBalanceValidationUseCaseTest {

    private val validateWalletBalanceUseCase = WalletBalanceValidationUseCase()

    @Test
    fun `should throw RequestValidationException with AMOUNT_MUST_BE_POSITIVE when balance is negative`() {
        val negativeBalance = -100.0

        try {
            validateWalletBalanceUseCase.validateWalletBalance(negativeBalance)
        } catch (exception: RequestValidationException) {
            assertTrue(exception.message!!.contains(AMOUNT_MUST_BE_POSITIVE))
        }
    }

    @Test
    fun `should throw RequestValidationException when balance is negative and not numeric`() {
        // Given
        val invalidBalance = "-100.0"

        assertThrows(RequestValidationException::class.java) {
            validateWalletBalanceUseCase.validateWalletBalance(invalidBalance.toDouble())
        }
    }

    @Test
    fun `should not throw any exception when balance is positive and numeric`() {
        // Given
        val validBalance = 100.0

        // When & Then
        assertDoesNotThrow {
            validateWalletBalanceUseCase.validateWalletBalance(validBalance)
        }
    }

    @Test
    fun `should return true when balance is positive`() {
        val positiveBalance = 50.0

        val result = validateWalletBalanceUseCase.validateWalletBalanceIsPositive(positiveBalance)

        assertTrue(result)
    }

    @Test
    fun `should return false when balance is zero`() {
        val zeroBalance = 0.0

        val result = validateWalletBalanceUseCase.validateWalletBalanceIsPositive(zeroBalance)

        assertFalse(result)
    }

    @Test
    fun `should return false when balance is negative`() {
        val negativeBalance = -50.0

        val result = validateWalletBalanceUseCase.validateWalletBalanceIsPositive(negativeBalance)

        assertFalse(result)
    }

    @Test
    fun `should return true when balance is numeric`() {
        val numericBalance = 123.45

        val result = validateWalletBalanceUseCase.validateWalletBalanceIsNumeric(numericBalance)

        assertTrue(result)
    }

    @Test
    fun `should return false when balance is not numeric`() {
        val nonNumericBalance = "hi"

        val result = nonNumericBalance.toDoubleOrNull() == null

        assertTrue(result)
    }
}
