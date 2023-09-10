package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class LoginScreenModel:
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
LoginScreenInteractionListener{

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
        TODO("Not yet implemented")
    }
}