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
        updateState { it.copy(keepLoggedIn = it.keepLoggedIn) }
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
        println("token${result.accessToken}")
        updateState { it.copy(isLoading = false, error = "null") }
        sendNewEffect(LoginScreenUIEffect.LoginEffect(""))
    }

    private fun onLoginFailed(error: ErrorState) {
        state.value.sheetState.show()
        handleErrorState(error)
        updateState { it.copy(isLoading = false, error = "error") }
    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            ErrorState.NoInternet -> {}
            ErrorState.NetworkNotSupported -> "ops"
            ErrorState.RequestFailed -> "ops"
            ErrorState.UnAuthorized -> "ops3"
            ErrorState.WifiDisabled -> {
                state.value.sheetState.show()
            }      // just for testing
            ErrorState.HasNoPermission -> "ops4"    // do nothing for now
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
        sendNewEffect(LoginScreenUIEffect.HasPermission)
        updateState { it.copy(showPermissionSheet = false) }
    }

    override fun onCancelClick() {
        state.value.sheetState.dismiss()
        updateState { it.copy(showPermissionSheet = false) }
    }
    // endregion
}
