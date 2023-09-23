package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import data.service.ILocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel

class MainScreenModel(val locationService: ILocationService):
    BaseScreenModel<MainScreenUiState, MainUiEffect>(MainScreenUiState()),
    MainScreenInteractionListener {
    override fun onCLickStart() {
        coroutineScope.launch(Dispatchers.IO) {
            val enabled = locationService.isGPSEnabled()
            println("testGPS: $enabled")
            if (enabled.not()) {
                println("testGPS: inside if condition")
                locationService.openLocationSettings()
            }
        }
        //sendNewEffect(MainUiEffect.MainEffect)
    }

}
