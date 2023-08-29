package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageAuthenticationUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel

class LoginScreenModel() :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {

    private val manageAuthentication: IManageAuthenticationUseCase by inject()

    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onUsernameChanged(username: String) {
        updateState { it.copy(userName = username) }

    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    override fun onKeepLoggedInChecked() {
        updateState { it.copy(keepLoggedIn = !it.keepLoggedIn) }
    }

    override fun onClickLogin(username: String, password: String, keepLoggedIn: Boolean) {
        // call useCase and validate data to login
    }

    override fun onClickSignUp() {
        // should send effect to navigate to sign up screen
    }

}
