package presentation.meals

import presentation.resturantDetails.RestaurantUIEffect
import presentation.search.SearchUiEffect

sealed interface MealsUiEffect {
    data object NavigateBack: MealsUiEffect
    data object NavigateToLogin : MealsUiEffect
    object onGoToCart : MealsUiEffect


}
