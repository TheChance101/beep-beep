package domain.usecase.validation

import domain.utils.AuthorizationException

interface IValidationUseCase {
    @Throws(AuthorizationException.InvalidUsernameException::class)
    fun validateUsername(username: String)

    @Throws(AuthorizationException.InvalidPasswordException::class)
    fun validatePassword(password: String)

    @Throws(AuthorizationException.InvalidFullNameException::class)
    fun validateFullName(fullName: String)

    @Throws(AuthorizationException.InvalidEmailException::class)
    fun validateEmail(email: String)

    @Throws(AuthorizationException.InvalidPhoneException::class)
    fun validatePhone(phone: String)
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

    override fun validateFullName(fullName: String) {
        if (fullNameRegex.matches(fullName).not()) {
            throw AuthorizationException.InvalidFullNameException
        }
    }

    override fun validateEmail(email: String) {
        if (emailRegex.matches(email).not()) {
            throw AuthorizationException.InvalidEmailException
        }
    }

    override fun validatePhone(phone: String) {
        if (phoneRegex.any { it.matches(phone) }.not()) {
            throw AuthorizationException.InvalidPhoneException
        }
    }

    private companion object {
        val fullNameRegex = "^[\\p{L}'-]+(?: [\\p{L}'-]+)*\$".toRegex()
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
        val phoneRegex = listOf(
            "^01\\d{9}\$".toRegex(),
            "^07\\d{9}\$".toRegex(),
            "^09\\d{9}\$".toRegex(),
            "^(05|09)\\d{9}\$".toRegex(),
            "^\\d{10}\$\n".toRegex()
        )
    }
}