package presentation.main

sealed interface MainUiEffect {
    data object MainEffect : MainUiEffect
}
