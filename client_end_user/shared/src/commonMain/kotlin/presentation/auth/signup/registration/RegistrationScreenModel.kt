package presentation.auth.signup.registration

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.validation.IValidationUseCase
import domain.utils.AuthorizationException
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class RegistrationScreenModel(private val validation: IValidationUseCase) :
    BaseScreenModel<RegistrationUIState, RegistrationScreenEffect>(RegistrationUIState()),
    RegistrationInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    // region interactions
    override fun onUsernameChanged(username: String) {
        updateState { it.copy(username = username) }
        tryCatch { validation.validateUsername(username) }
    }

    override fun onEmailChanged(email: String) {
        updateState { it.copy(email = email) }
        tryCatch { validation.validateEmail(email) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
        tryCatch { validation.validatePassword(password) }
    }

    override fun onNextButtonClicked() {
        with(state.value) {
            tryCatch {
                validation.validateUsername(username)
                validation.validatePassword(password)
                sendNewEffect(
                    RegistrationScreenEffect.NavigateToSubmitRegistrationScreen(
                        username,
                        email,
                        password
                    )
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
            clearErrors()
        } catch (e: AuthorizationException.InvalidUsernameException) {
            updateState { it.copy(isUsernameError = true) }
        } catch (e: AuthorizationException.InvalidEmailException) {
            updateState {
                it.copy(isEmailError = true)
            }
        } catch (e: AuthorizationException.InvalidPasswordException) {
            updateState { it.copy(isPasswordError = true) }
        }
    }

    private fun clearErrors() {
        updateState {
            it.copy(
                isUsernameError = false,
                isPasswordError = false,
                isEmailError = false,
            )
        }
    }
    // endregion
}