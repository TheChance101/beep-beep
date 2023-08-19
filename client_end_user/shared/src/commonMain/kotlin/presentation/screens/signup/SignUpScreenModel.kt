package presentation.screens.signup

import cafe.adriel.voyager.core.model.StateScreenModel
import domain.usecase.UserAuthenticationUseCase

class SignUpScreenModel(private val userAuthenticationUseCase: UserAuthenticationUseCase) :
    StateScreenModel<SignUpUiState>(SignUpUiState()) {

}