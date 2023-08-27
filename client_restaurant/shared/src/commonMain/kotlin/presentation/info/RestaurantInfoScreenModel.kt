package presentation.info

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IManageRestaurantInfoUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantInfoScreenModel(private val manageRestaurantInfo: IManageRestaurantInfoUseCase) :
    BaseScreenModel<RestaurantInfoUiState, RestaurantInfoUiEffect>(RestaurantInfoUiState()),
    RestaurantInfoInteractionListener {
    override val viewModelScope: CoroutineScope
        get() = coroutineScope


    init {
        getRestaurantInfo()
    }

    private fun getRestaurantInfo() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            {
                manageRestaurantInfo
                    .getRestaurantInfo()
            },
            ::onGetRestaurantInfoSuccess,
            ::onGetRestaurantInfoError
        )
    }

    private fun onGetRestaurantInfoSuccess(restaurant: Restaurant) {
        val result = restaurant.toUiState()
        updateState {
            it.copy(
                restaurant = result,
                isLoading = false,
                error = null
            )
        }
    }

    private fun onGetRestaurantInfoError(e: ErrorState) {
        updateState { it.copy(isLoading = false, error = e) }
    }

    override fun onRestaurantNameChange(name: String) {
        updateState { it.copy(restaurant = it.restaurant.copy(restaurantName = name)) }
    }

    override fun onPhoneNumberChange(phoneNum: String) {
        updateState { it.copy(restaurant = it.restaurant.copy(phoneNumber = phoneNum)) }
    }

    override fun onOpeningTimeChange(openingTime: String) {
        updateState { it.copy(restaurant = it.restaurant.copy(openingTime = openingTime)) }
    }

    override fun onClosingTimeChange(closingTime: String) {
        updateState { it.copy(restaurant = it.restaurant.copy(closingTime = closingTime)) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(restaurant = it.restaurant.copy(description = description)) }
    }


    override fun onClickSave() {
        val restaurant = state.value.restaurant.toRestaurant()
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { manageRestaurantInfo.updateRestaurantInfo(restaurant) },
            ::onUpdateRestaurantInfoSuccess,
            ::onUpdateRestaurantInfoError
        )
    }

    private fun onUpdateRestaurantInfoSuccess(result: Boolean) {
        updateState { it.copy(isLoading = false) }
        println(result)
    }

    private fun onUpdateRestaurantInfoError(e: ErrorState) {
        updateState { it.copy(isLoading = false, error = e) }
    }

    override fun onClickLogout() {
        sendNewEffect(RestaurantInfoUiEffect.NavigateToLogin)
    }

    override fun onClickBackArrow() {
        sendNewEffect(RestaurantInfoUiEffect.NavigateUp)
    }
}