package presentation.main

import data.service.ILocationService
import presentation.base.BaseScreenModel

class MainScreenModel(val locationService: ILocationService):
    BaseScreenModel<MainScreenUiState, MainUiEffect>(MainScreenUiState()),
    MainScreenInteractionListener {
    override fun onCLickStart() {
        val enabled = locationService.isGPSEnabled()
        println("testGPS: $enabled")
        if (enabled.not()) {
            println("testGPS: inside if condition")
            locationService.openLocationSettings()
        }
        //sendNewEffect(MainUiEffect.MainEffect)
    }

}
