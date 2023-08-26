package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.ILoginUserUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class LoginScreenModel:
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {
    private val loginUserUseCase: ILoginUserUseCase by inject()

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    override fun onUserNameChanged(userName: String) {
        updateState { it.copy(userName = userName) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    override fun onKeepLoggedInClicked() {
        updateState { it.copy(keepLoggedIn = !it.keepLoggedIn) }
    }


    override fun onLoginClicked(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean
    ) {

//        updateState {
//            it.copy(
//                error = null,
//                usernameErrorMsg = "",
//                isUsernameError = false,
//                isPasswordError = false,
//                passwordErrorMsg = ""
//            )
//        }

        tryToExecute(
            {
                loginUserUseCase.loginUser(
                    userName,
                    password,
                    isKeepMeLoggedInChecked
                )
            },
            onSuccess = { onLoginSuccess() },
            onError = ::onLoginFailed
        )
    }

    private fun onLoginSuccess() {
        updateState { it.copy(isLoading = false, error = null) }
        sendNewEffect(LoginScreenUIEffect.LoginEffect(""))
    }

    private fun onLoginFailed(error: ErrorState) {
        updateState { it.copy(isLoading = false, error = null) }
        handleErrorState(error)

    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            ErrorState.NoInternet -> {}
            ErrorState.NetworkNotSupported -> {}
            ErrorState.RequestFailed -> {}
            ErrorState.UnAuthorized -> {}
            ErrorState.WifiDisabled -> {}
            ErrorState.HasNoPermission -> {}
            ErrorState.InvalidCredentials -> {
                updateState {
                    it.copy(
                        error = error
                    )
                }
            }

            is ErrorState.InvalidPassword -> updateState {
                it.copy(
                    passwordErrorMsg = "error.errorMessage",
                    isPasswordError = true
                )
            }

            is ErrorState.InvalidUserName -> {
                updateState {
                    it.copy(
                        usernameErrorMsg = error.errorMessage,
                        isUsernameError = true
                    )
                }
            }

            is ErrorState.UserNotFound -> {
                updateState {
                    it.copy(
                        usernameErrorMsg = error.errorMessage,
                        isUsernameError = true
                    )
                }
            }
        }
    }

    // region permission
    override fun onRestaurantNameChanged(restaurantName: String) {
        updateState { it.copy(restaurantName = restaurantName) }
    }

    override fun onOwnerEmailChanged(ownerEmail: String) {
        updateState { it.copy(ownerEmail = ownerEmail) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
    }

    override fun onRequestPermissionClicked() {
        updateState { it.copy(showPermissionSheet = true) }
    }

    override fun onSubmitClicked() {
        state.value.sheetState.dismiss()
        updateState { it.copy(showPermissionSheet = false) }
    }

    override fun onCancelClicked() {
        state.value.sheetState.dismiss()
        updateState { it.copy(showPermissionSheet = false) }
    }
    // endregion

}
