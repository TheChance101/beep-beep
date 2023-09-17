package presentation.auth.signup.registration

import presentation.base.BaseInteractionListener

interface RegistrationInteractionListener : BaseInteractionListener {
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onNextButtonClicked()
    fun onBackButtonClicked()
}