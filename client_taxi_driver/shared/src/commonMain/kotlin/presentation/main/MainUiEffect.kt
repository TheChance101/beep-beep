package presentation.main

sealed interface MainUiEffect {
    data object Start : MainUiEffect
}
