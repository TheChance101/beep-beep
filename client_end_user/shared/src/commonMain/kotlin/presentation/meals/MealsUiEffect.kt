package presentation.meals

import presentation.search.SearchUiEffect

sealed interface MealsUiEffect {
    data object NavigateBack: MealsUiEffect
    data object NavigateToLogin : MealsUiEffect

}
