package presentation.home

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IGetCuisinesUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import kotlin.random.Random

class HomeScreenModel(private val cuisineUseCase: IGetCuisinesUseCase) :
    BaseScreenModel<HomeScreenUiState, HomeScreenUiEffect>(HomeScreenUiState()),
    HomeScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getPopularCuisines()
    }

    override fun onClickCuisineItem(cuisineId: String) {
        sendNewEffect(HomeScreenUiEffect.CuisineUiEffect(cuisineId))
    }

    override fun onclickSeeAllCuisines() {
        getAllCuisines()
    }

    private fun getPopularCuisines() {
        tryToExecute(
            { cuisineUseCase.getCuisines().toCuisineUiState() },
            ::onGetCuisinesSuccess,
            ::onGetCuisinesError
        )
    }

    private fun getAllCuisines() {
        tryToExecute(
            { cuisineUseCase.getCuisines().toCuisineUiState() },
            ::onGetAllCuisinesSuccess,
            ::onGetCuisinesError
        )
    }

    private fun onGetAllCuisinesSuccess(cuisines: List<CuisineUiState>) {
        updateState { it.copy(cuisines = cuisines) }
    }

    private fun onGetCuisinesSuccess(cuisines: List<CuisineUiState>) {
        val popularCuisines = cuisines.shuffled().take(4)
        updateState { it.copy(popularCuisines = popularCuisines) }
    }

    private fun onGetCuisinesError(error: ErrorState) {
        TODO("Not yet implemented")
    }

}