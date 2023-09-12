package presentation.auth.signup.registrationSubmit

import presentation.base.BaseInteractionListener

interface RegistrationSubmitInteractionListener : BaseInteractionListener {
    fun onFullNameChanged(fullName: String)
    fun onEmailChanged(email: String)
    fun onPhoneChanged(phone: String)
    fun onSignUpButtonClicked()
    fun onBackButtonClicked()
}