package presentation.preferredFood

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.usecase.IExploreRestaurantUseCase
import domain.usecase.IManageSettingUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.cuisines.toCuisineUiState

class PreferredFoodScreenModel(
    private val mangePreferredFood: IManageSettingUseCase,
    private val manageRestaurants: IExploreRestaurantUseCase
) : BaseScreenModel<PreferredFoodUIState, PreferredFoodUIEffect>(PreferredFoodUIState()),
    PreferredFoodInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope
    private var selectedFood = mutableListOf<String>()

    init {
        getData()
    }

    private fun getData() {
        tryToExecute(
            manageRestaurants::getCuisines,
            ::onGetCuisinesSuccess,
            ::onError
        )
    }

    private fun onGetCuisinesSuccess(cuisines: List<Cuisine>) {
        updateState { it.copy(preferredFood = cuisines.toCuisineUiState()) }
    }


    override fun onPreferredFoodClicked(id: String) {
        selectedFood.add(id)
        if (selectedFood.size < 4) {
            addPreferredFood(id)
        } else {
            onSaved()
        }

    }

    private fun onSaved() {
        tryToExecute(
            { mangePreferredFood.savePreferredFood(state.value.selectedPreferredFood) },
            ::onSavedPreferredFoodSuccess,
            ::onError
        )
    }

    private fun addPreferredFood(id: String) {
        val updatedPreferredCuisine = state.value.preferredFood.filterNot { it.cuisineId == id }
        updateState {
            it.copy(
                preferredFood = updatedPreferredCuisine,
                selectedPreferredFood = selectedFood
            )
        }
    }

    private fun onSavedPreferredFoodSuccess(unit: Unit) {
        sendNewEffect(PreferredFoodUIEffect.NavigateToPreferredScreen)
    }

    private fun onError(errorState: ErrorState) {
        println("Error :$errorState")
    }
}
