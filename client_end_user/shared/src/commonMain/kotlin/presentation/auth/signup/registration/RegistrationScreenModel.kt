package presentation.auth.signup.registration

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.validation.IValidationUseCase
import domain.utils.AuthorizationException
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import resources.strings.IStringResources

class RegistrationScreenModel(
    private val validation: IValidationUseCase,
    private val stringResources: IStringResources
) :
    BaseScreenModel<RegistrationUIState, RegistrationScreenEffect>(RegistrationUIState()),
    RegistrationInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    // region interactions
    override fun onUsernameChanged(username: String) {
        updateState { it.copy(username = username) }
        tryCatch {
            validation.validateUsername(username)
            clearErrors()
        }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
        tryCatch {
            validation.validatePassword(password)
            clearErrors()
        }
    }

    override fun onNextButtonClicked() {
        with(state.value) {
            tryCatch {
                validation.validateUsername(username); validation.validatePassword(password)
                sendNewEffect(
                    RegistrationScreenEffect.NavigateToSubmitRegistrationScreen(username, password)
                )
            }
        }
    }

    override fun onBackButtonClicked() {
        sendNewEffect(RegistrationScreenEffect.NavigateBack)
    }
    // endregion

    // region private methods
    private fun tryCatch(block: () -> Unit) {
        try {
            block()
        } catch (e: AuthorizationException.InvalidUsernameException) {
            updateState {
                it.copy(
                    usernameErrorMsg = e.message ?: stringResources.invalidUsername,
                    isUsernameError = true
                )
            }
        } catch (e: AuthorizationException.InvalidPasswordException) {
            updateState {
                it.copy(
                    passwordErrorMsg = e.message ?: stringResources.invalidPassword,
                    isPasswordError = true
                )
            }
        }
    }

    private fun clearErrors() {
        updateState {
            it.copy(
                usernameErrorMsg = "",
                passwordErrorMsg = "",
                isUsernameError = false,
                isPasswordError = false
            )
        }
    }
    // endregion
}