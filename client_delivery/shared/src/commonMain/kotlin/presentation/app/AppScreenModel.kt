package presentation.app

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageLoginUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppScreenModel(private val manageUser: IManageLoginUserUseCase): ScreenModel {
    private val _isKeptLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isKeptLoggedIn: StateFlow<Boolean> = _isKeptLoggedIn.asStateFlow()

    init {
        getIsKeptLoggedIn()
    }
    private fun getIsKeptLoggedIn(){
        coroutineScope.launch(Dispatchers.IO) {
            _isKeptLoggedIn.update { manageUser.getKeepMeLoggedInFlag() }
        }
    }
}