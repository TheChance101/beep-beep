package org.thechance.service_identity.domain.usecases.validation

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.CreateUserRequest
import org.thechance.service_identity.domain.entity.RequestValidationException
import org.thechance.service_identity.domain.entity.UpdateUserRequest
import org.thechance.service_identity.domain.util.*

interface IUserInfoValidationUseCase {

    fun validateUserInformation(user: CreateUserRequest)

    fun validateUpdateUserInformation(user: UpdateUserRequest)

    fun validateUsernameIsNotEmpty(username: String): Boolean

    fun validateFullNameIsNotEmpty(fullName: String): Boolean

    fun validatePasswordIsNotEmpty(password: String): Boolean

    fun validateUsername(username: String): Boolean

    fun validatePasswordLength(password: String): Boolean

    fun validateEmail(email: String): Boolean
}

@Single
class UserInfoValidationUseCase() : IUserInfoValidationUseCase {

    override fun validateUserInformation(user: CreateUserRequest) {
        val reasons = mutableListOf<String>()

        if (!validateUsernameIsNotEmpty(user.username)) {
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

    override fun validateUpdateUserInformation(user: UpdateUserRequest) {
        val reasons = mutableListOf<String>()

        user.username?.let {
            if (!validateUsernameIsNotEmpty(it)) {
                reasons.add(USERNAME_CANNOT_BE_BLANK)
            }
        }

        user.username?.let {
            if (!validateUsername(it)) {
                reasons.add(INVALID_USERNAME)
            }
        }

        user.fullName?.let {
            if (!validateFullNameIsNotEmpty(it)) {
                reasons.add(INVALID_FULLNAME)
            }
        }

        user.password?.let {
            if (!validatePasswordIsNotEmpty(it)) {
                reasons.add(PASSWORD_CANNOT_BE_BLANK)
            }
        }

        user.password?.let {
            if (!validatePasswordLength(it)) {
                reasons.add(PASSWORD_CANNOT_BE_LESS_THAN_8_CHARACTERS)
            }
        }

        user.email?.let {
            if (!validateEmail(it)) {
                reasons.add(INVALID_EMAIL)
            }
        }

        if (reasons.isNotEmpty()) {
            throw RequestValidationException(reasons)
        }
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