package presentation.cuisines

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.usecase.IExploreRestaurantUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.base.ErrorState.NoInternet

class CuisinesScreenModel(
    private val manageRestaurant: IExploreRestaurantUseCase,
) : BaseScreenModel<CuisinesUiState, CuisinesUiEffect>(CuisinesUiState()),
    CuisinesInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getData()
    }

    private fun getData() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            manageRestaurant::getCuisines,
            ::onGetCuisinesSuccess,
            ::onError
        )
    }

    private fun onGetCuisinesSuccess(cuisines: List<Cuisine>) {
        updateState {
            it.copy(
                error = null,
                isLoading = false,
                cuisines = cuisines.toCuisineUiState()
            )
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(isLoading = false) }
        when (errorState) {
            is NoInternet -> {
                updateState { it.copy(error = errorState) }
            }

            else -> {
                updateState { it.copy(error = errorState) }
            }
        }
    }

    // region interactions
    override fun onCuisineClicked(cuisineId: String) {
        val cuisine = state.value.cuisines.first { it.cuisineId == cuisineId }
        sendNewEffect(
            CuisinesUiEffect.NavigateToCuisineDetails(
                cuisineId = cuisineId,
                cuisineName = cuisine.cuisineName
            )
        )
    }

    override fun onBackIconClicked() {
        sendNewEffect(CuisinesUiEffect.NavigateBack)
    }
    // endregion
}
