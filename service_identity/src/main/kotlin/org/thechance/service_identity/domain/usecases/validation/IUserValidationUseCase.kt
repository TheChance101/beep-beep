package org.thechance.service_identity.domain.usecases.validation

import org.koin.core.annotation.Single

interface IUserValidationUseCase {

    fun validateUsernameIsNotBlank(username: String): Boolean

    fun validateFullNameIsNotEmpty(fullName: String): Boolean

    fun validatePasswordIsNotEmpty(password: String): Boolean

    fun validateUsername(username: String): Boolean

    fun validatePasswordLength(password: String): Boolean
}

@Single
class UserValidationUseCase : IUserValidationUseCase {

    override fun validateUsernameIsNotBlank(username: String): Boolean = username.isBlank()

    override fun validateUsername(username: String): Boolean {
        val validUserNameRegex = "[a-zA-Z0-9_]+".toRegex()
        return username.matches(validUserNameRegex)
    }

    override fun validateFullNameIsNotEmpty(fullName: String): Boolean = fullName.isEmpty()

    override fun validatePasswordIsNotEmpty(password: String): Boolean = password.isEmpty()

    override fun validatePasswordLength(password: String): Boolean = password.length < 8

}