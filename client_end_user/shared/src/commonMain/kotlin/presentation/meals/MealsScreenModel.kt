package presentation.meals

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Meal
import domain.usecase.IMangeRestaurantUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.cuisines.CuisinesUiEffect
import presentation.resturantDetails.MealInteractionListener
import presentation.resturantDetails.toUIState
import org.koin.core.component.get

class MealsScreenModel(
    private val cuisineId: String,
    private val cuisineName: String,
    private val manageRestaurant: IMangeRestaurantUseCase
) :
    BaseScreenModel<MealsUiState, MealsUiEffect>(MealsUiState()), MealsInteractionListener,
    MealInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getData()
    }

    private fun getData() {
        updateState { it.copy(isLoading = true, cuisineName = cuisineName) }
        tryToExecute(
            { manageRestaurant.getMealsInCuisine(cuisineId) },
            ::onGetMealsSuccess,
            ::onError
        )
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(isLoading = false) }
        when (errorState) {
            is ErrorState.NoInternet -> {
                updateState { it.copy(error = errorState) }
            }

            else -> {
                updateState { it.copy(error = errorState) }
            }
        }
    }

    private fun onGetMealsSuccess(meals: List<Meal>) {
        updateState { it.copy(meals = meals.toUIState(), isLoading = false, error = null) }
    }

    override fun onIncreaseMealQuantity() {
        TODO("Not yet implemented")
    }

    override fun onDecreaseMealQuantity() {
        TODO("Not yet implemented")
    }

    override fun onAddToCart() {
        TODO("Not yet implemented")
    }

    override fun onDismissSheet() {
        TODO("Not yet implemented")
    }

    override fun onBackClicked() {
        sendNewEffect(MealsUiEffect.NavigateBack)
    }

}
