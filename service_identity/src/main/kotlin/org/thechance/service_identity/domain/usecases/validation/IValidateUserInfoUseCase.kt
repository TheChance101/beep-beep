package org.thechance.service_identity.domain.usecases.validation

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.RequestValidationException
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.usecases.util.*

interface IValidateUserInfoUseCase {

    fun validateUserInformation(user: User)

    fun validateUpdateUserInformation(user: User)

    fun validateUsernameIsNotBlank(username: String): Boolean

    fun validateFullNameIsNotEmpty(fullName: String): Boolean

    fun validatePasswordIsNotEmpty(password: String): Boolean

    fun validateUsername(username: String): Boolean

    fun validatePasswordLength(password: String): Boolean

    fun validateEmail(email: String): Boolean
}

@Single
class ValidateUserInfoUseCase : IValidateUserInfoUseCase {

    override fun validateUserInformation(user: User) {
        val reasons = mutableListOf<String>()

        if (!validateUsernameIsNotBlank(user.username)) {
            reasons.add(USERNAME_CANNOT_BE_BLANK)
        }

        if (!validateUsername(user.username)) {
            reasons.add(INVALID_USERNAME)
        }

        if (!validateFullNameIsNotEmpty(user.fullName)) {
            reasons.add(INVALID_FULLNAME)
        }

        if (!validatePasswordIsNotEmpty(user.password)) {
            reasons.add(PASSWORD_CANNOT_BE_BLANK)
        }

        if (!validatePasswordLength(user.password)) {
            reasons.add(PASSWORD_CANNOT_BE_LESS_THAN_8_CHARACTERS)
        }

        if (!validateEmail(user.email)) {
            reasons.add(INVALID_EMAIL)
        }

        if (reasons.isNotEmpty()) {
            throw RequestValidationException(reasons)
        }
    }

    override fun validateUpdateUserInformation(user: User) {
        val reasons = mutableListOf<String>()

        if (user.username.isNotBlank() && !validateUsername(user.username)) {
            reasons.add(INVALID_USERNAME)
        }

        if (user.password.isNotBlank() && !validatePasswordLength(user.password)) {
            reasons.add(PASSWORD_CANNOT_BE_LESS_THAN_8_CHARACTERS)
        }

        if (user.email.isNotBlank() && !validateEmail(user.email)) {
            reasons.add(INVALID_EMAIL)
        }

        if (reasons.isNotEmpty()) {
            throw RequestValidationException(reasons)
        }
    }

    override fun validateUsernameIsNotBlank(username: String): Boolean = username.isNotBlank()

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