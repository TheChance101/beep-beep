package presentation.taxi.destinationSearch

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Meal
import domain.entity.Restaurant
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageCartUseCase
import domain.usecase.ISearchUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.resturantDetails.MealUIState
import presentation.resturantDetails.toUIState

class SearchDestinationScreenModel() : BaseScreenModel<SearchDestinationUiState, SearchDestinationUiEffect>(SearchDestinationUiState()), SearchDestinationInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope
    private var searchJob: Job? = null

    init {

    }


    override fun onSearchInputChanged(keyword: String) {
        updateState { it.copy(query = keyword) }
        launchSearchJob()
    }


    private fun launchSearchJob() {
        searchJob?.cancel()
//        searchJob = launchDelayed(300L) { this@SearchDestinationScreenModel.getMealAndRestaurant() }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error) }
    }

}
