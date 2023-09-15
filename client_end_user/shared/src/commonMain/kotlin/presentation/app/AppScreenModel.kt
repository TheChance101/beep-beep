package presentation.app

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageUserUseCase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppScreenModel(private val manageUser: IManageUserUseCase) : ScreenModel {



     suspend fun getInitScreen(): Boolean {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Boolean>()

            val isFirstTimeOpenApp = manageUser.getIsFirstTimeUseApp()

            if (isFirstTimeOpenApp) {
                manageUser.saveIsFirstTimeUseApp(false)
                deferred.complete(isFirstTimeOpenApp)
            } else {
                deferred.complete(false)

            }
            deferred.await()
        }
    }
}