package presentation.home

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.GetFavoriteRestaurantsUseCase
import domain.usecase.IGetCuisinesUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class HomeScreenModel(
    private val cuisineUseCase: IGetCuisinesUseCase,
    private val getFavoriteRestaurantsUseCase: GetFavoriteRestaurantsUseCase
) :
    BaseScreenModel<HomeScreenUiState, HomeScreenUiEffect>(HomeScreenUiState()),
    HomeScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getRecommendedCuisines()
        getFavoriteRestaurants()
    }

    override fun onClickCuisineItem(cuisineId: String) {
        sendNewEffect(HomeScreenUiEffect.NavigateToCuisineDetails(cuisineId))
    }

    override fun onclickSeeAllCuisines() {
        sendNewEffect(HomeScreenUiEffect.NavigateToCuisines)
    }

    override fun onClickChatSupport() {
        sendNewEffect(HomeScreenUiEffect.NavigateToChatSupport)
    }

    override fun onClickOrderTaxi() {
        sendNewEffect(HomeScreenUiEffect.NavigateToOrderTaxi)
    }

    override fun onClickOrderFood() {
        sendNewEffect(HomeScreenUiEffect.ScrollDownToRecommendedRestaurants)
    }

    private fun getRecommendedCuisines() {
        tryToExecute(
            { cuisineUseCase.getCuisines().toCuisineUiState() },
            ::onGetCuisinesSuccess,
            ::onGetCuisinesError
        )
    }

    private fun onGetCuisinesSuccess(cuisines: List<CuisineUiState>) {
        val popularCuisines = cuisines.shuffled().take(4)
        updateState { it.copy(recommendedCuisines = popularCuisines) }
    }

    private fun onGetCuisinesError(error: ErrorState) {
        println("error is $error")
    }

    private fun getFavoriteRestaurants() {
        tryToExecute(
            { getFavoriteRestaurantsUseCase() },
            ::onGetFavoriteRestaurantsSuccess,
            ::onGetFavoriteRestaurantsError
        )
    }

    private fun onGetFavoriteRestaurantsSuccess(restaurants: List<Restaurant>) {
        updateState { it.copy(favoriteRestaurants = restaurants.toRestaurantUiState()) }
    }

    private fun onGetFavoriteRestaurantsError(error: ErrorState) {
        println("error is $error")
    }

}