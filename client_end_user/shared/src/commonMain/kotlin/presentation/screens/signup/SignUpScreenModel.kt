package presentation.screens.signup

import cafe.adriel.voyager.core.model.StateScreenModel
import domain.usecase.UserAuthenticationUseCase
import kotlinx.coroutines.flow.update

class SignUpScreenModel(private val userAuthenticationUseCase: UserAuthenticationUseCase) :
    StateScreenModel<SignUpUiState>(SignUpUiState()) {

    suspend fun signUp(fullName: String, userName: String, password: String, email: String) {
        val isSuccess = userAuthenticationUseCase.createUser(fullName, userName, password, email)
        if (isSuccess)
            mutableState.update { it.copy(isSuccess = false) }
        else
            mutableState.update { it.copy(error = "") }
    }

}