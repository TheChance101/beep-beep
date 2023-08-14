package org.thechance.service_identity.domain.usecases.validation


import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UserValidationUseCaseTest {

    private val userValidationUseCase = UserInfoValidationUseCase()

    @Test
    fun should_ReturnFalse_When_TheUsernameIsEmpty() {
        assertFalse(userValidationUseCase.validateUsernameIsNotEmpty(""))
    }

    @Test
    fun should_ReturnTrue_When_TheUsernameIsNotEmpty() {
        val name = "Youssef"
        val result = userValidationUseCase.validateUsernameIsNotEmpty(name)
        assertTrue(result)
    }

    @Test
    fun should_ReturnTrue_When_UsernameMatchesValidFormat() {
        val username = "youssef_Ramadan"
        val result = userValidationUseCase.validateUsername(username)
        assertTrue(result)
    }

    @Test
    fun should_ReturnFalse_When_UsernameContainsInvalidCharacters() {
        val username = "!@#@!#.test"
        val result = userValidationUseCase.validateUsername(username)
        assertFalse(result)
    }

    @Test
    fun should_ReturnFalse_When_FullNameIsBlank() {
        val fullName = ""
        val result = userValidationUseCase.validateFullNameIsNotEmpty(fullName)
        assertFalse(result)
    }

    @Test
    fun should_ReturnTrue_When_FullNameIsNotBlank() {
        val fullName = "test"
        val result = userValidationUseCase.validateFullNameIsNotEmpty(fullName)
        assertTrue(result)
    }

    @Test
    fun should_ReturnFalse_When_PasswordIsBlank() {
        val password = ""
        val result = userValidationUseCase.validatePasswordIsNotEmpty(password)
        assertFalse(result)
    }

    @Test
    fun should_ReturnTrue_When_PasswordIsNotBlank() {
        val password = "password123"
        val result = userValidationUseCase.validatePasswordIsNotEmpty(password)
        assertTrue(result)
    }

    @Test
    fun should_ReturnTrue_When_PasswordHasValidLength() {
        val password = "longpassword"
        val result = userValidationUseCase.validatePasswordLength(password)
        assertTrue(result)
    }

    @Test
    fun should_ReturnFalse_When_PasswordHasInvalidLength() {
        val password = "short"
        val result = userValidationUseCase.validatePasswordLength(password)
        assertFalse(result)
    }

    @Test
    fun should_ReturnTrue_When_EmailIsValid() {
        val email = "test@example.com"
        val result = userValidationUseCase.validateEmail(email)
        assertTrue(result)
    }

    @Test
    fun should_ReturnFalse_When_EmailIsInvalid() {
        val email = "test_invalid.email"
        val result = userValidationUseCase.validateEmail(email)
        assertFalse(result)
    }
}