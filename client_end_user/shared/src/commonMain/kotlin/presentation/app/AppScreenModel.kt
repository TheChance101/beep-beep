package presentation.app

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import util.LanguageCode

class AppScreenModel(private val manageUser: IManageUserUseCase) : ScreenModel {

    private val _language: MutableStateFlow<LanguageCode> = MutableStateFlow(LanguageCode.EN)
    val language: StateFlow<LanguageCode> = _language.asStateFlow()

    private val _isFirstTimeOpenApp: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFirstTimeOpenApp: StateFlow<Boolean> = _isFirstTimeOpenApp.asStateFlow()

    init {
        getUserLanguageCode()
        getInitScreen()
    }

    private fun getUserLanguageCode() {
        coroutineScope.launch(Dispatchers.IO) {
            manageUser.getUserLanguageCode().distinctUntilChanged().collectLatest { lang ->
                _language.update {
                    LanguageCode.entries.find { languageCode -> languageCode.value == lang }
                        ?: LanguageCode.EN
                }
            }
        }
    }

    private fun getInitScreen() {
        coroutineScope.launch(Dispatchers.IO) {
            _isFirstTimeOpenApp.update { manageUser.getIsFirstTimeUseApp() }
        }
    }
}
