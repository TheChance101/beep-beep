package presentation.meals

import androidx.paging.PagingData
import domain.entity.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import presentation.base.ErrorState
import presentation.resturantDetails.MealUIState


data class MealsUiState(
    val cuisineName: String = "",
    val meals: Flow<PagingData<MealUIState>> = emptyFlow(),

    val showMealSheet: Boolean = false,
    val selectedMeal: MealUIState = MealUIState(),

    val isLogin: Boolean = false,
    val showLoginSheet: Boolean = false,
    val isAddToCartLoading: Boolean = false,
    val showToast: Boolean = false,

    val isLoading: Boolean = false,
    val error: ErrorState? = null,
    val errorAddToCart: ErrorState? = null,
)
