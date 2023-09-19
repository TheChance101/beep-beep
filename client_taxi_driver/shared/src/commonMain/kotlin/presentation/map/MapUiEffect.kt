package presentation.map

sealed interface MapUiEffect {
    data object PopUp : MapUiEffect
}