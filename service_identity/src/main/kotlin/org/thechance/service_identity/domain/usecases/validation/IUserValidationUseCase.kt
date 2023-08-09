package org.thechance.service_identity.domain.usecases.validation

import org.koin.core.annotation.Single

interface IUserValidationUseCase {

    fun validateUsernameIsNotBlank(username: String): Boolean

    fun validateFullNameIsNotEmpty(fullName: String): Boolean

    fun validatePasswordIsNotEmpty(password: String): Boolean

    fun validateUsername(username: String): Boolean

    fun validatePasswordLength(password: String): Boolean

    fun validateEmail(email: String): Boolean
}

@Single
class UserValidationUseCase : IUserValidationUseCase {

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