package presentation.app

import cafe.adriel.voyager.core.model.ScreenModel
import domain.usecase.ILoginUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

class AppScreenModel(private val manageUser: ILoginUserUseCase) : ScreenModel {

    private val _appState: MutableStateFlow<AppUiState> = MutableStateFlow(AppUiState())
    val appState: StateFlow<AppUiState> = _appState.asStateFlow()


    init {
        runBlocking {
            val isFirstTimeOpenAppDeferred = async(Dispatchers.IO) {
                manageUser.getRestaurantId().isEmpty()
            }

            val isKeptLoggedInDeferred = async(Dispatchers.IO) {
                manageUser.getKeepMeLoggedInFlag()
            }

            val hasMultipleRestaurantsDeferred = async(Dispatchers.IO) {
                manageUser.getNumberOfRestaurants() > 1
            }

            _appState.update {
                it.copy(
                    isFirstTimeOpenApp = isFirstTimeOpenAppDeferred.await(),
                    isKeptLoggedIn = isKeptLoggedInDeferred.await(),
                    hasMultipleRestaurants = hasMultipleRestaurantsDeferred.await(),
                )
            }
            println("isFirstTimeOpenApp:${appState.value.isFirstTimeOpenApp}")
            println("isKeptLoggedIn:${appState.value.isKeptLoggedIn}")
            println("hasMultipleRestaurants:${appState.value.hasMultipleRestaurants}")
        }
    }
}