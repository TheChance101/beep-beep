package presentation.meals

sealed interface MealsUiEffect {
    data object NavigateBack: MealsUiEffect

}
