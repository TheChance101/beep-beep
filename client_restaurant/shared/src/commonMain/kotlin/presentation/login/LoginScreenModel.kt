package presentation.login

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class LoginScreenModel : StateScreenModel<LoginScreenUIState>(LoginScreenUIState()), KoinComponent {

    fun onEmailChanged(email: String) {
        updateState { it.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    private fun updateState(update: (LoginScreenUIState) -> LoginScreenUIState) {
        mutableState.update(update)
    }
}