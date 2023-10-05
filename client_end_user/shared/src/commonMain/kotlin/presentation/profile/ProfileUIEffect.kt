package presentation.profile

sealed class ProfileUIEffect {
    data object NavigateToLoginScreen : ProfileUIEffect()
}
