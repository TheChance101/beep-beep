package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageLocationUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MainScreenModel(
    private val location: IManageLocationUseCase,
): BaseScreenModel<MainScreenUiState, MainUiEffect>(MainScreenUiState()),
    MainScreenInteractionListener {

    override fun onClickStart() {
        tryToExecute(
            function = location::startTracking,
            onSuccess = { onSuccess() },
            onError = ::onError
        )
    }

    private fun onSuccess() {
        sendNewEffect(MainUiEffect.Start)
    }

    private fun onError(error: ErrorState) {
        when (error) {
            ErrorState.LocationPermissionDenied("LocationPermissionDenied") ->  showSnackBar()
            else -> {}
        }
    }
    private fun showSnackBar() {
        coroutineScope.launch {
            updateState { it.copy(showSnackBar = true) }
            delay(4000)
            updateState { it.copy(showSnackBar = false) }
        }

    }

}
