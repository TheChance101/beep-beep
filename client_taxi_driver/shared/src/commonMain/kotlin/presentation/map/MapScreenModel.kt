package presentation.map

import presentation.base.BaseScreenModel

class MapScreenModel : BaseScreenModel<MapScreenUiState, MapUiEffect>(MapScreenUiState()),
    MapScreenInteractionListener {
}