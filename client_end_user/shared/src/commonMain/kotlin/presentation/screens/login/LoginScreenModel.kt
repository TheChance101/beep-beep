package presentation.screens.login

import cafe.adriel.voyager.core.model.StateScreenModel
import domain.usecase.UserAuthenticationUseCase

class LoginScreenModel(private val userAuthenticationUseCase: UserAuthenticationUseCase) :
    StateScreenModel<LoginUiState>(LoginUiState()) {
}