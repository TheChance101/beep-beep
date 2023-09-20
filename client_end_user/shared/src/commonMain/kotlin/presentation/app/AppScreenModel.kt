package presentation.app

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import util.LanguageCode

class AppScreenModel(private val manageUser: IManageUserUseCase) : ScreenModel {

    private val _language: MutableStateFlow<LanguageCode> = MutableStateFlow(LanguageCode.EG)
    val language: StateFlow<LanguageCode> = _language.asStateFlow()


    init {
        getUserLanguageCode()
    }

    private fun getUserLanguageCode() {
        coroutineScope.launch(Dispatchers.IO) {
            manageUser.getUserLanguageCode().distinctUntilChanged().collect { lang ->
                _language.update {
                    LanguageCode.entries.find { languageCode -> languageCode.value == lang }
                        ?: LanguageCode.EG
                }
            }
        }
    }

    suspend fun getInitScreen(): Boolean {
        return withContext(Dispatchers.IO) {
            val isFirstTimeOpenApp = manageUser.getIsFirstTimeUseApp()

            if (isFirstTimeOpenApp) {
                manageUser.saveIsFirstTimeUseApp(false)
            }
            isFirstTimeOpenApp
        }
    }
}
