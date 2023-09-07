package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.User
import domain.usecase.IManageUserUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState


class MainScreenModel(private val manageUserUseCase: IManageUserUseCase) :
    BaseScreenModel<MainUiState, MainScreenUiEffect>(MainUiState()),
    MainInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        tryToExecute(
            { manageUserUseCase.getUserWallet() },
            ::onLoginSuccess,
            ::onError
        )
    }

    private fun onLoginSuccess(user: User) {
        updateState { user.toUIState() }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(isLogin = false) }
    }

    override fun onLoginClicked() {
        sendNewEffect(MainScreenUiEffect.NavigateLoginScreen)
    }


}
