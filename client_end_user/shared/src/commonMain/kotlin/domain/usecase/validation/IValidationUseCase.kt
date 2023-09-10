package domain.usecase.validation

import domain.utils.AuthorizationException

interface IValidationUseCase {
    @Throws(AuthorizationException.InvalidUsernameException::class)
    fun validateUsername(username: String)
    @Throws(AuthorizationException.InvalidPasswordException::class)
    fun validatePassword(password: String)
}

class ValidationUseCaseUseCase : IValidationUseCase {
    override fun validateUsername(username: String) {
        if (username.isEmpty() || "[a-zA-Z0-9_]+".toRegex().matches(username).not()) {
            throw AuthorizationException.InvalidUsernameException
        }
    }

    override fun validatePassword(password: String) {
        if (password.isEmpty() || password.length < 8) {
            throw AuthorizationException.InvalidPasswordException
        }
    }
}