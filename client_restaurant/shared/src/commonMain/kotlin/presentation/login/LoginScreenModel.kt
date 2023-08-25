package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.UserTokens
import domain.usecase.ILoginUserUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class LoginScreenModel(private val loginUserUseCase: ILoginUserUseCase) :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    override fun onUserNameChanged(userName: String) {
        updateState { it.copy(userName = userName) }
    }

    override fun onOwnerEmailChanged(ownerEmail: String) {
        updateState { it.copy(ownerEmail = ownerEmail) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    override fun onKeepLoggedInClicked() {
        updateState { it.copy(keepLoggedIn = it.keepLoggedIn) }
    }

    override fun onNameChanged(restaurantName: String) {
        updateState { it.copy(restaurantName = restaurantName) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
    }

    override fun onRequestPermissionClick() {

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
        handleErrorState(error)
        updateState { it.copy(isLoading = false, error = "error") }
    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            ErrorState.NoInternet -> {}
            ErrorState.NetworkNotSupported -> "opsops"
            ErrorState.RequestFailed -> "ops"
            ErrorState.UnAuthorized -> "ops"
            ErrorState.WifiDisabled -> "ops2"
        }
    }

    override fun onClickSubmit() {

    }

    override fun onCancelClick() {

    }
}
