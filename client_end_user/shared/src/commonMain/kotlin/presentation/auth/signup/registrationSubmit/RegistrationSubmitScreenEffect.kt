package presentation.auth.signup.registrationSubmit

sealed interface RegistrationSubmitScreenEffect {
    data object NavigateToLoginScreen : RegistrationSubmitScreenEffect
    data object NavigateBack : RegistrationSubmitScreenEffect
}
