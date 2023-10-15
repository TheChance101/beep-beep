package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageUserLocationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MainScreenModel(private val manageUserLocation: IManageUserLocationUseCase) :
    BaseScreenModel<MainUiState, MainScreenUiEffect>(MainUiState()),
    MainInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    override fun onClickStart() {
        tryToExecute(
            function = manageUserLocation::startTracking,
            onSuccess = { onSuccess() },
            onError = ::onError
        )
    }

    private fun onSuccess() {
        sendNewEffect(MainScreenUiEffect.Start)
    }

    private fun onError(error: ErrorState) {
        when (error) {
            ErrorState.LocationAccessDenied -> showSnackBar("To continue, Please access location permission")
            else -> {}
        }
    }

    private fun showSnackBar(message: String) {
        viewModelScope.launch {
            updateState { it.copy(showSnackBar = true, snackBarMessage = message) }
            delay(4000)
            updateState { it.copy(showSnackBar = false, snackBarMessage = message) }
        }

    }

}