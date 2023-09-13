package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel

class MainScreenModel(private val permissionsController: BpPermissionController):
    BaseScreenModel<MainScreenUiState, MainUiEffect>(MainScreenUiState()),
    MainScreenInteractionListener {

    override fun getLocationPermission() {
        coroutineScope.launch {
            permissionsController.getLocationPermission()
        }
    }

}
