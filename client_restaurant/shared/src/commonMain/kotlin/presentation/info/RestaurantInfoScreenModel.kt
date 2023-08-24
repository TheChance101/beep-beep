package presentation.info

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IManageRestaurantInfoUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantInfoScreenModel :
    BaseScreenModel<RestaurantInfoUiState, RestaurantInfoUiEffect>(RestaurantInfoUiState()),
    RestaurantInfoInteractionListener {
    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val manageRestaurantInfoUseCase: IManageRestaurantInfoUseCase by inject()

    init {
        getRestaurantInfo()
    }

    private fun getRestaurantInfo() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            {
                manageRestaurantInfoUseCase
                    .getRestaurantInfo("7c3d631e-6d49-48c9-9f91-9426ec559eb1")
            },
            ::onGetRestaurantInfoSuccess,
            ::onGetRestaurantInfoError
        )
    }

    private fun onGetRestaurantInfoSuccess(restaurant: Restaurant) {
        val result = restaurant.toUiState()
        updateState { result.copy(isLoading = false) }
    }

    private fun onGetRestaurantInfoError(e: ErrorState) {
        updateState { it.copy(isLoading = false) }
        sendNewEffect(RestaurantInfoUiEffect.ShowNoDataPlaceholder)
    }

    override fun onRestaurantNameChange(name: String) {
        updateState { it.copy(restaurantName = name) }
    }

    override fun onPhoneNumberChange(phoneNum: String) {
        updateState { it.copy(phoneNumber = phoneNum) }
    }

    override fun onOpeningTimeChange(openingTime: String) {
        updateState { it.copy(openingTime = openingTime) }
    }

    override fun onClosingTimeChange(closingTime: String) {
        updateState { it.copy(closingTime = closingTime) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
    }


    override fun onClickSave() {
        val restaurant = state.value.toRestaurant()
        updateState { it.copy(isLoading = true) }
        if (manageRestaurantInfoUseCase.validateRestaurantInfo(restaurant)) {
            tryToExecute(
                { manageRestaurantInfoUseCase.updateRestaurantInfo(restaurant) },
                ::onUpdateRestaurantInfoSuccess,
                ::onUpdateRestaurantInfoError
            )
        }
    }

    private fun onUpdateRestaurantInfoSuccess(restaurant: Restaurant) {
        updateState { it.copy(isLoading = false) }
        sendNewEffect(RestaurantInfoUiEffect.ShowSaveInfoSuccess("Info saved successfully"))
    }

    private fun onUpdateRestaurantInfoError(e: ErrorState) {
        updateState { it.copy(isLoading = false) }
        sendNewEffect(RestaurantInfoUiEffect.ShowErrorMessage("Error in update restaurantInfo $e"))
    }

    override fun onClickLogout() {
        sendNewEffect(RestaurantInfoUiEffect.NavigateToLogin)
    }

    override fun onClickBackArrow() {
        sendNewEffect(RestaurantInfoUiEffect.NavigateUp)
    }
}