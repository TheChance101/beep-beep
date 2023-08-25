package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class LoginScreenModel :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    override fun onEmailChanged(email: String) {
        updateState { it.copy(email = email) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    override fun onClickLogin() {
        sendNewEffect(LoginScreenUIEffect.Login("1"))
    }
}
