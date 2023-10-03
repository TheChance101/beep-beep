package presentation.search

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Explore
import domain.entity.InProgressWrapper
import domain.usecase.IGetCuisinesUseCase
import domain.usecase.ISearchUseCase
import io.realm.kotlin.query.RealmElementQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

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
                meals = result.meals.toUiState()
            )
        }
    }

    private fun onError(error: ErrorState) {

    }
}
