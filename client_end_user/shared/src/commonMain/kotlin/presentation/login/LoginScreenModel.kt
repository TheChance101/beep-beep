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

}
