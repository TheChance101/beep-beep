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
    fun validatePhone(phone: String,currency: String)


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

    override fun validatePhone(phone: String, currency: String) {
        val phoneRegex = getPhoneRegex(currency)
        if(phone.matches(Regex(phoneRegex)).not()){
            throw AuthorizationException.InvalidPhoneException
        }
    }


    private fun getPhoneRegex(currency: String): String {
        return when (currency) {
            "EGP" -> "^01\\d{9}\$"
            "IQD" -> "^07\\d{9}\$"
            "SYP" -> "^09\\d{9}\$"
            "ILS" -> "^(05|09)\\d{9}\$"
            else -> "^\\d{10}\$"
        }
    }

    private companion object {
        val fullNameRegex = "^[\\p{L}'-]+(?: [\\p{L}'-]+)*\$".toRegex()
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
    }
}