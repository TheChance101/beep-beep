package org.thechance.common.presentation.login

import org.thechance.common.domain.usecase.ILoginUserUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.restaurant.ErrorWrapper
import org.thechance.common.presentation.util.ErrorState


class LoginScreenModel(
    private val login: ILoginUserUseCase
) : BaseScreenModel<LoginUIState, LoginUIEffect>(LoginUIState()), LoginInteractionListener {

    override fun onPasswordChange(password: String) {
        updateState { it.copy(password = password) }
        updateLoginClickedState()
    }

    override fun onUsernameChange(username: String) {
        updateState { it.copy(username = username) }
        updateLoginClickedState()
    }

    private fun updateLoginClickedState(){
        updateState { it.copy( isEnable = state.value.username.isNotBlank() && state.value.password.isNotBlank()) }
    }
    override fun onLoginClicked() {
        clearState()
        updateState { it.copy(isLoading = true, isEnable = false) }
        mutableState.value.apply {
            tryToExecute(
                { login.loginUser(username = username, password = password) },
                { onLoginSuccess() },
                ::onError
            )
        }
    }

    private fun onLoginSuccess() {
        updateState { it.copy(isLoading = false) }
        sendNewEffect(LoginUIEffect.LoginSuccess)
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(isLoading = false, isEnable = true) }
        handleErrorState(error)
    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            is ErrorState.MultipleErrors -> {
                val errorStates = error.errors
                updateState {
                    it.copy(
                        isPasswordError = errorStates.firstInstanceOfOrNull<ErrorState.InvalidPassword>()
                            ?.let { error ->
                                ErrorWrapper(error.errorMessage, true)
                            },
                        isUserError = errorStates.firstInstanceOfOrNull<ErrorState.InvalidUserName>()
                            ?.let { error ->
                                ErrorWrapper(error.errorMessage, true)
                            },
                    )
                }
                updateState {
                    errorStates.firstInstanceOfOrNull<ErrorState.InvalidPermission>()?.errorMessage?.let { errorMessage ->
                            it.copy(isSnackBarVisible = errorMessage.isNotEmpty(), snackBarTitle = errorMessage)
                        }?:it
                }
            }

            ErrorState.NoConnection -> {
                updateState {
                    it.copy(
                        isSnackBarVisible = true,
                        snackBarTitle = null,
                    )
                }
            }

            else -> {
                updateState { it.copy(isSnackBarVisible = true, snackBarTitle = null) }
            }
        }
    }

    override fun onSnackBarDismiss() {
        updateState {
            it.copy(
                isSnackBarVisible = false,
            )
        }
    }

    private fun clearState() {
        updateState {
            it.copy(
                isLoading = false,
                isPasswordError = ErrorWrapper(),
                isUserError = ErrorWrapper(),
                isSnackBarVisible = false,
                snackBarTitle = null,
            )
        }
    }

}