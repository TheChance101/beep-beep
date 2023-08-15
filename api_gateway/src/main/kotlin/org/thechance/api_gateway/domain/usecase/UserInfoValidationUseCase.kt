package org.thechance.api_gateway.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.api_gateway.util.*

interface IUserInfoValidationUseCase {

    fun validateUserInformation(fullName: String,username: String, password: String, email: String)
    fun validateUser(username: String, password: String)

    fun validateUsernameIsNotEmpty(username: String): Boolean

    fun validateFullNameIsNotEmpty(fullName: String): Boolean

    fun validatePasswordIsNotEmpty(password: String): Boolean

    fun validateUsername(username: String): Boolean

    fun validatePasswordLength(password: String): Boolean

    fun validateEmail(email: String): Boolean
}

@Single
class UserInfoValidationUseCase() : IUserInfoValidationUseCase {

    override fun validateUserInformation(fullName: String,username: String, password: String, email: String) {
        val reasons = mutableListOf<String>()

        if (!validateUsernameIsNotEmpty(username)) {
            reasons.add(USERNAME_CANNOT_BE_BLANK)
        }

        if (!validateUsername(username)) {
            reasons.add(INVALID_USERNAME)
        }

        if (!validateFullNameIsNotEmpty(fullName)) {
            reasons.add(INVALID_FULL_NAME)
        }

        if (!validatePasswordIsNotEmpty(password)) {
            reasons.add(PASSWORD_CANNOT_BE_BLANK)
        }

        if (!validatePasswordLength(password)) {
            reasons.add(PASSWORD_CANNOT_BE_LESS_THAN_8_CHARACTERS)
        }

        if (!validateEmail(email)) {
            reasons.add(INVALID_EMAIL)
        }

        if (reasons.isNotEmpty()) {
            throw RequestValidationException(reasons)
        }
    }

    override fun validateUser(username: String, password: String) {

    }

    override fun validateUsernameIsNotEmpty(username: String): Boolean = username.isNotBlank()

    override fun validateUsername(username: String): Boolean {
        val validUserNameRegex = "[a-zA-Z0-9_]+".toRegex()
        return username.matches(validUserNameRegex)
    }

    override fun validateFullNameIsNotEmpty(fullName: String): Boolean = fullName.isNotBlank()

    override fun validatePasswordIsNotEmpty(password: String): Boolean = password.isNotBlank()

    override fun validatePasswordLength(password: String): Boolean = password.length > 8

    override fun validateEmail(email: String): Boolean {
        val validEmailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
        return email.matches(validEmailRegex)
    }

}