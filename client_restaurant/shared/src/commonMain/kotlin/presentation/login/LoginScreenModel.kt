package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IAuthenticationUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel

class LoginScreenModel :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    override fun onEmailChanged(email: String) {
        updateState { it.copy(email = email) }
    }

    override fun onOwnerEmailChanged(ownerEmail: String) {
        updateState { it.copy(ownerEmail = ownerEmail) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    override fun onNameChanged(restaurantName: String) {
        updateState { it.copy(restaurantName = restaurantName) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
    }

    override fun onClickLogin() {

        sendNewEffect(LoginScreenUIEffect.Login)
    }

    override fun onClickSubmit() {
        sendNewEffect(LoginScreenUIEffect.Permission)
    }

    override fun onClickCancel() {
        TODO("Not yet implemented")
    }
}
