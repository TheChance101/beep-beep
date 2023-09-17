package presentation.app

import cafe.adriel.voyager.core.model.ScreenModel
import domain.usecase.IManageUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class AppScreenModel(private val manageUser: IManageUserUseCase) : ScreenModel {



     suspend fun getInitScreen(): Boolean {
        return withContext(Dispatchers.IO) {
            val isFirstTimeOpenApp = manageUser.getIsFirstTimeUseApp()

            if (isFirstTimeOpenApp) {
                manageUser.saveIsFirstTimeUseApp(false)
                isFirstTimeOpenApp
            }
            isFirstTimeOpenApp
        }
    }
}