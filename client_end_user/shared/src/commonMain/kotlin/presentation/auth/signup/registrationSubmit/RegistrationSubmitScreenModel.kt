package presentation.auth.signup.registrationSubmit

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageUserUseCase
import domain.usecase.validation.IValidationUseCase
import domain.utils.AuthorizationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
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
import resources.strings.IStringResources
import util.LanguageCode

class RegistrationSubmitScreenModel(
    private val username: String,
    private val password: String,
    private val validation: IValidationUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase,
    private val manageUser: IManageUserUseCase,
    private val stringResources: IStringResources
) : BaseScreenModel<RegistrationSubmitUIState, RegistrationSubmitScreenEffect>(
    RegistrationSubmitUIState()
),
    RegistrationSubmitInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope
    private val _language: MutableStateFlow<LanguageCode> = MutableStateFlow(LanguageCode.EN)
    val language: StateFlow<LanguageCode> = _language.asStateFlow()
    init {
        getUserLanguageCode()
    }
    private fun getUserLanguageCode() {
        coroutineScope.launch(Dispatchers.IO) {
            manageUser.getUserLanguageCode().distinctUntilChanged().collectLatest { lang ->
                _language.update {
                    LanguageCode.entries.find { languageCode -> languageCode.value == lang }
                        ?: LanguageCode.EN
                }
            }
        }
    }


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
        tryCatch { validation.validatePhone(phone,_language.value.value); clearErrors() }
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
            showSnackbar(stringResources.oppsRegistrationNotCompleted)
        }
    }

    private fun onError(error: ErrorState) {
        clearErrors()
        when (error) {
            InvalidFullName -> updateState {
                it.copy(fullErrorMsg = stringResources.invalidFullName, isFullNameError = true)
            }

            InvalidEmail -> updateState {
                it.copy(emailErrorMsg = stringResources.invalidEmail, isEmailError = true)
            }

            InvalidPhone -> updateState {
                it.copy(phoneErrorMsg = stringResources.invalidPhoneNumber, isPhoneError = true)
            }

            NoInternet -> showSnackbar(stringResources.noInternet)
            RequestFailed -> showSnackbar(stringResources.requestFailed)
            is UserAlreadyExists -> showSnackbar(error.message)
            WifiDisabled -> showSnackbar(stringResources.wifiDisabled)
            else -> { showSnackbar(stringResources.unknownError) }
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
            updateState { it.copy(fullErrorMsg = e.message ?: stringResources.invalidFullName, isFullNameError = true) }
        } catch (e: AuthorizationException.InvalidEmailException) {
            updateState {
                it.copy(emailErrorMsg = e.message ?: stringResources.invalidEmail, isEmailError = true)
            }
        } catch (e: AuthorizationException.InvalidPhoneException) {
            updateState {
                it.copy(phoneErrorMsg = e.message ?: stringResources.invalidPhoneNumber, isPhoneError = true)
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