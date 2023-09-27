package presentation.map


sealed interface MapScreenUiEffect {
    data object CloseMap : MapScreenUiEffect
}