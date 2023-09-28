package presentation.auth.signup.registrationSubmit

import presentation.base.BaseInteractionListener

interface RegistrationSubmitInteractionListener : BaseInteractionListener {
    fun onFullNameChanged(fullName: String)
    fun onPhoneChanged(phone: String)
    fun onAddressChanged(address: String)
    fun onSignUpButtonClicked()
    fun onBackButtonClicked()
}