package presentation.auth.signup.registrationSubmit

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.validation.IValidationUseCase
import domain.utils.AuthorizationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.base.ErrorState.InvalidEmail
import presentation.base.ErrorState.InvalidFullName
import presentation.base.ErrorState.InvalidPhone
import presentation.base.ErrorState.NoInternet
import presentation.base.ErrorState.RequestFailed
import presentation.base.ErrorState.UserAlreadyExists
import presentation.base.ErrorState.WifiDisabled

class RegistrationSubmitScreenModel(
    private val username: String,
    private val password: String,
    private val validation: IValidationUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase
) : BaseScreenModel<RegistrationSubmitUIState, RegistrationSubmitScreenEffect>(
    RegistrationSubmitUIState()
),
    RegistrationSubmitInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope


    // region interactions
    override fun onFullNameChanged(fullName: String) {
        updateState { it.copy(fullName = fullName) }
        tryCatch { validation.validateFullName(fullName); clearErrors() }
    }

    override fun onEmailChanged(email: String) {
        updateState { it.copy(email = email) }
        tryCatch { validation.validateEmail(email); clearErrors() }
    }

    override fun onPhoneChanged(phone: String) {
        updateState { it.copy(phone = phone) }
        tryCatch { validation.validatePhone(phone); clearErrors() }
    }

    override fun onSignUpButtonClicked() {
        with(state.value) {
            updateState { it.copy(isLoading = true) }
            tryToExecute(
                function = { manageAuthentication.createUser(fullName, username, password, email) },
                onSuccess = ::onRegistrationSuccess,
                onError = ::onError
            )
        }
    }

    private fun onRegistrationSuccess(success: Boolean) {
        if (success) {
            sendNewEffect(RegistrationSubmitScreenEffect.NavigateToLoginScreen)
        } else {
            showSnackbar("Opps, Registration not completed")
        }
    }

    private fun onError(error: ErrorState) {
        clearErrors()
        when (error) {
            InvalidFullName -> updateState {
                it.copy(fullErrorMsg = "Invalid Name", isFullNameError = true)
            }

            InvalidEmail -> updateState {
                it.copy(emailErrorMsg = "Invalid Email", isEmailError = true)
            }

            InvalidPhone -> updateState {
                it.copy(phoneErrorMsg = "Invalid Phone Number", isPhoneError = true)
            }

            NoInternet -> showSnackbar("No Internet Connection")
            RequestFailed -> showSnackbar("Request Failed")
            is UserAlreadyExists -> showSnackbar(error.message)
            WifiDisabled -> showSnackbar("WIFI Disabled")
            else -> { showSnackbar("Unknown Error") }
        }
    }

    override fun onBackButtonClicked() {
        sendNewEffect(RegistrationSubmitScreenEffect.NavigateBack)
    }
    // endregion

    // region private methods
    private fun tryCatch(block: () -> Unit) {
        try {
            block()
        } catch (e: AuthorizationException.InvalidFullNameException) {
            updateState { it.copy(fullErrorMsg = e.message ?: "Invalid Name", isFullNameError = true) }
        } catch (e: AuthorizationException.InvalidEmailException) {
            updateState {
                it.copy(emailErrorMsg = e.message ?: "Invalid Email", isEmailError = true)
            }
        } catch (e: AuthorizationException.InvalidPhoneException) {
            updateState {
                it.copy(phoneErrorMsg = e.message ?: "Invalid Phone Number", isPhoneError = true)
            }
        }
    }

    private fun clearErrors() {
        updateState {
            it.copy(
                fullErrorMsg = "",
                isFullNameError = false,
                emailErrorMsg = "",
                isEmailError = false,
                phoneErrorMsg = "",
                isPhoneError = false,
                isLoading = false
            )
        }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            updateState { it.copy(snackbarMessage = message, showSnackbar = true) }
            delay(2000) // wait for snackbar to show
            updateState { it.copy(showSnackbar = false) }
            delay(300) // wait for snackbar to hide
            updateState { it.copy(snackbarMessage = "") }
        }
    }
    // endregion
}