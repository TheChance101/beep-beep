package presentation.search

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Explore
import domain.usecase.ISearchUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.resturantDetails.MealUIState
import presentation.resturantDetails.toUIState

class SearchScreenModel(private val search: ISearchUseCase) :
    BaseScreenModel<SearchUiState, SearchUiEffect>(SearchUiState()), SearchInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope
    private var searchJob: Job? = null

    init {
        getMealAndRestaurant()
    }

    override fun onSearchInputChanged(keyword: String) {
        updateState { it.copy(query = keyword) }
        launchSearchJob()
    }

    override fun onRestaurantClicked(restaurantId: String) {
        sendNewEffect(SearchUiEffect.NavigateToRestaurant(restaurantId))
    }

    override fun onMealClicked(meal: MealUIState) {
        updateState { it.copy(showMealSheet = true, selectedMeal = meal) }
    }

    override fun onDismissSheet() {
        updateState { it.copy(showMealSheet = false) }
    }

    private fun launchSearchJob() {
        searchJob?.cancel()
        searchJob = launchDelayed(300L) { this@SearchScreenModel.getMealAndRestaurant() }
    }

    private fun getMealAndRestaurant() {
        tryToExecute(
            { search.searchMealAndRestaurant(query = state.value.query) },
            ::onSuccess,
            ::onError
        )
    }

    private fun onSuccess(result: Explore) {
        updateState {
            it.copy(
                restaurants = result.restaurants.toExploreUiState(),
                meals = result.meals.toUIState()
            )
        }
    }

    private fun onError(error: ErrorState) {

    }

}