package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import domain.dataSource.IBpLocationDataSource
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel

class MainScreenModel(private val permissionsController: IBpLocationDataSource):
    BaseScreenModel<MainScreenUiState, MainUiEffect>(MainScreenUiState()),
    MainScreenInteractionListener {

    override fun getLocationPermission() {
        coroutineScope.launch {
            permissionsController.getCurrentLocation().distinctUntilChanged().collect {
                println("current location: lat: ${it.first}, long: ${it.second}")
            }
        }
    }
}
