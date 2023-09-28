package presentation.auth.signup.registration

sealed interface RegistrationScreenEffect {
    data class NavigateToSubmitRegistrationScreen(
        val username: String,
        val email: String,
        val password: String
    ) : RegistrationScreenEffect

    data object NavigateBack : RegistrationScreenEffect
}
