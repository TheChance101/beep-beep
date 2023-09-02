package presentation.information

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IManageRestaurantInfoUseCase
import domain.usecase.ValidateRestaurantInfoUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantInformationScreenModel(
    private val manageRestaurantInformation: IManageRestaurantInfoUseCase,
    private val restaurantInformationValidation: ValidateRestaurantInfoUseCase
) : BaseScreenModel<RestaurantInformationUiState, RestaurantInformationUiEffect>
    (RestaurantInformationUiState()), RestaurantInformationInteractionListener {
    override val viewModelScope: CoroutineScope
        get() = coroutineScope


    init {
        getRestaurantInfo()
    }

    private fun getRestaurantInfo() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            {
                manageRestaurantInformation
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

    private fun onGetRestaurantInfoError(error: ErrorState) {
        updateState { it.copy(isLoading = false, error = error) }
    }

    override fun onRestaurantNameChange(name: String) {
        val validationResult = restaurantInformationValidation.isRestaurantNameValid(name)
        updateState {
            it.copy(
                restaurant = it.restaurant.copy(
                    restaurantName = name,
                    isRestaurantNameError = !validationResult
                )
            )
        }
        onUpdateRestaurantInformation()
    }

    override fun onPhoneNumberChange(phoneNum: String) {
        val validationResult = restaurantInformationValidation.isPhoneNumberValid(phoneNum)
        updateState {
            it.copy(
                restaurant = it.restaurant.copy(
                    phoneNumber = phoneNum,
                    isPhoneNumberError = !validationResult
                )
            )
        }
        onUpdateRestaurantInformation()
    }

    override fun onOpeningTimeChange(openingTime: String) {
        val validationResult = restaurantInformationValidation.isTimeValid(openingTime)
        updateState {
            it.copy(
                restaurant = it.restaurant.copy(
                    openingTime = openingTime,
                    isOpeningTimeError = !validationResult
                )
            )
        }
        onUpdateRestaurantInformation()
    }

    override fun onClosingTimeChange(closingTime: String) {
        val validationResult = restaurantInformationValidation.isTimeValid(closingTime)
        updateState {
            it.copy(
                restaurant = it.restaurant.copy(
                    closingTime = closingTime,
                    isClosingTimeError = !validationResult
                )
            )
        }
        onUpdateRestaurantInformation()
    }

    override fun onDescriptionChanged(description: String) {
        val validationResult = restaurantInformationValidation.isDescriptionLengthValid(description)
        updateState {
            it.copy(
                restaurant = it.restaurant.copy(
                    description = description,
                    isDescriptionLengthError = !validationResult
                )
            )
        }
        onUpdateRestaurantInformation()
    }


    override fun onClickSave() {
        val restaurant = state.value.restaurant.toRestaurant()
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { manageRestaurantInformation.updateRestaurantInformation(restaurant) },
            ::onUpdateRestaurantInfoSuccess,
            ::onUpdateRestaurantInfoError
        )
    }

    private fun onUpdateRestaurantInformation() {
        val state = state.value.restaurant
        val validationResult = restaurantInformationValidation.isRestaurantInformationValid(
            state.restaurantName,
            state.openingTime,
            state.closingTime,
            state.phoneNumber,
            state.description,
        )
        val updatedState = state.copy(isSaveButtonEnabled = validationResult)
        updateState { it.copy(restaurant = updatedState) }
    }

    private fun onUpdateRestaurantInfoSuccess(result: Boolean) {
        updateState { it.copy(isLoading = false) }
    }

    private fun onUpdateRestaurantInfoError(error: ErrorState) {
        updateState { it.copy(isLoading = false, error = error) }
    }

    override fun onClickLogout() {
        sendNewEffect(RestaurantInformationUiEffect.NavigateToLogin)
    }

    override fun onClickBackArrow() {
        sendNewEffect(RestaurantInformationUiEffect.NavigateUp)
    }
}