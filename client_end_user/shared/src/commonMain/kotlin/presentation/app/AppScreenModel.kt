package presentation.app

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import util.LanguageCode

class AppScreenModel(private val manageUser: IManageUserUseCase) : ScreenModel {

    val language = MutableStateFlow(LanguageCode.EN)

    init {
        println("AppScreenModel: ${this.hashCode()}")
        println("AppScreenModel: init screen model")
        coroutineScope.launch {
            println("iAppScreenModel: before update")
            delay(5000)
            language.update { LanguageCode.AR }
            println("AppScreenModel: after update")

        }
    }

    suspend fun getUserLanguageCode(): String {
        return withContext(Dispatchers.IO) {
            manageUser.getUserLanguageCode()
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
