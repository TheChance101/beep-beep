package presentation.auth.signup.registrationSubmit

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.UserCreation
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
import presentation.base.ErrorState.InvalidFullName
import presentation.base.ErrorState.InvalidPhone
import presentation.base.ErrorState.UserAlreadyExists
import util.LanguageCode

class RegistrationSubmitScreenModel(
    private val username: String,
    private val email: String,
    private val password: String,
    private val validation: IValidationUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase,
    private val manageUser: IManageUserUseCase,
) : BaseScreenModel<RegistrationSubmitUIState, RegistrationSubmitScreenEffect>(
    RegistrationSubmitUIState()
), RegistrationSubmitInteractionListener {
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
        tryCatch { validation.validateFullName(fullName) }
    }

    override fun onPhoneChanged(phone: String) {
        updateState { it.copy(phone = phone) }
        tryCatch { validation.validatePhone(phone,_language.value.value) }
    }

    override fun onAddressChanged(address: String) {
        updateState { it.copy(address = address) }
        tryCatch { validation.validateAddress(address) }
    }

    override fun onSignUpButtonClicked() {
        with(state.value) {
            updateState { it.copy(isLoading = true) }
            tryToExecute(
                function = {
                    manageAuthentication.createUser(
                        UserCreation(
                            fullName,
                            username,
                            password,
                            email,
                            phone,
                            address
                        )
                    )
                },
                onSuccess = ::onRegistrationSuccess,
                onError = ::onError
            )
        }
    }

    private fun onRegistrationSuccess(success: Boolean) {
        sendNewEffect(RegistrationSubmitScreenEffect.NavigateToLoginScreen)
    }

    private fun onError(error: ErrorState) {
        clearErrors()
        when (error) {
            InvalidFullName -> updateState { it.copy(isFullNameError = true) }

            InvalidPhone -> updateState { it.copy(isPhoneError = true) }

            is UserAlreadyExists -> showSnackbar(error.message)
            else -> {}
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
            clearErrors()
        } catch (e: AuthorizationException.InvalidFullNameException) {
            updateState { it.copy(isFullNameError = true) }
        } catch (e: AuthorizationException.InvalidPhoneException) {
            updateState {
                it.copy(isPhoneError = true)
            }
        } catch (e: AuthorizationException.InvalidAddressException){
            updateState {
                it.copy(isAddressError = true)
            }
        }
    }

    private fun clearErrors() {
        updateState {
            it.copy(
                isFullNameError = false,
                isPhoneError = false,
                isAddressError = false,
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