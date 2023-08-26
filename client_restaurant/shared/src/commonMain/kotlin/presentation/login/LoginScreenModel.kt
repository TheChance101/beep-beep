package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.UserTokens
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

    override fun onClickLogin(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean
    ) {
        tryToExecute(
            { loginUserUseCase.loginUser(userName, password, isKeepMeLoggedInChecked) },
            this::onLoginSuccess,
            this::onLoginFailed
        )
    }

    private fun onLoginSuccess(result: UserTokens) {
        updateState {
            it.copy(
                isLoading = false,
                isSuccess = true,
                usernameErrorMsg = "",
                passwordErrorMsg = ""
            )
        }
        sendNewEffect(LoginScreenUIEffect.LoginEffect(""))
    }

    private fun onLoginFailed(error: ErrorState) {
        state.value.sheetState.show()
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
            is ErrorState.InvalidCredentials -> {
                updateState {
                    it.copy(
                        error = ErrorState.InvalidCredentials
                    )
                }
            }

            is ErrorState.UserNotExist -> {
                updateState {
                    it.copy(
                        error = ErrorState.UserNotExist("UserNotExist")
                    )
                }
            }

            is ErrorState.InvalidPassword -> updateState {
                it.copy(
                    passwordErrorMsg = "Invalid Password!"
                )
            }

            is ErrorState.InvalidUserName -> updateState {
                it.copy(
                    usernameErrorMsg = "Invalid UserName!"
                )
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

    override fun onRequestPermissionClick() {
        updateState { it.copy(showPermissionSheet = true) }
    }

    override fun onClickSubmit() {
        state.value.sheetState.dismiss()
        updateState { it.copy(showPermissionSheet = false) }
    }

    override fun onCancelClick() {
        state.value.sheetState.dismiss()
        updateState { it.copy(showPermissionSheet = false) }
    }
    // endregion
}
