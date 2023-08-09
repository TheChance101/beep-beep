package org.thechance.service_identity.domain.usecases.validation


import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UserValidationUseCaseTest {

    private val userValidationUseCase = ValidateUserInfoUseCase()

    @Test
    fun should_ReturnFalse_When_TheUsernameIsBlank() {
        assertFalse(userValidationUseCase.validateUsernameIsNotBlank(""))
    }

    @Test
    fun should_ReturnTrue_When_TheUsernameIsNotBlank() {
        assertTrue(userValidationUseCase.validateUsernameIsNotBlank("Youssef"))
    }

    @Test
    fun should_ReturnTrue_When_UsernameMatchesValidFormat() {
        assertTrue(userValidationUseCase.validateUsername("youssef_Ramadan"))
        assertTrue(userValidationUseCase.validateUsername("Youssef123"))
    }

    @Test
    fun should_ReturnFalse_When_UsernameContainsInvalidCharacters() {
        assertFalse(userValidationUseCase.validateUsername("test#"))
        assertFalse(userValidationUseCase.validateUsername("!@#@!#.doe"))
        assertFalse(userValidationUseCase.validateUsername("test@gmail"))
    }

    @Test
    fun should_ReturnFalse_When_FullNameIsBlank() {
        assertFalse(userValidationUseCase.validateFullNameIsNotEmpty(""))
    }

    @Test
    fun should_ReturnTrue_When_FullNameIsNotBlank() {
        assertTrue(userValidationUseCase.validateFullNameIsNotEmpty("test"))
    }

    @Test
    fun should_ReturnFalse_When_PasswordIsBlank() {
        assertFalse(userValidationUseCase.validatePasswordIsNotEmpty(""))
    }

    @Test
    fun should_ReturnTrue_When_PasswordIsNotBlank() {
        assertTrue(userValidationUseCase.validatePasswordIsNotEmpty("password123"))
    }

    @Test
    fun should_ReturnTrue_When_PasswordHasValidLength() {
        assertTrue(userValidationUseCase.validatePasswordLength("longpassword"))
    }

    @Test
    fun should_ReturnFalse_When_PasswordHasInvalidLength() {
        assertFalse(userValidationUseCase.validatePasswordLength("short"))
    }
}