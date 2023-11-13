package presentation.information

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IManageRestaurantInformationUseCase
import domain.usecase.IValidateRestaurantInfoUseCase
import domain.usecase.LogoutUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.get
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantInformationScreenModel(
    private val manageRestaurantInformation: IManageRestaurantInformationUseCase,
    private val restaurantInformationValidation: IValidateRestaurantInfoUseCase,
    private val logoutUser: LogoutUserUseCase,
) : BaseScreenModel<RestaurantInformationUiState, RestaurantInformationUiEffect>
    (RestaurantInformationUiState()), RestaurantInformationInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope


    init {
        getRestaurantInfo(get())
    }

    private fun onError(error: ErrorState) {
        updateState {
            it.copy(
                isLoading = false, error = error,
                restaurant = state.value.restaurant.copy(
                    isSaveButtonEnabled = true
                ),
                snackBarState = false
            )
        }
        handleErrorState(error)
        showSnackBar()
    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            ErrorState.UnAuthorized -> {
                sendNewEffect(RestaurantInformationUiEffect.LogoutSuccess)
            }

            ErrorState.NoInternet -> {
                sendNewEffect(RestaurantInformationUiEffect.ShowNoInternetError)
            }

            else -> {
                sendNewEffect(RestaurantInformationUiEffect.ShowUnknownError)
            }
        }
    }

    private fun getRestaurantInfo(id: String) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { manageRestaurantInformation.getRestaurantInfo(id) },
            ::onGetRestaurantInfoSuccess,
            ::onError
        )
    }

    private fun onGetRestaurantInfoSuccess(restaurant: Restaurant) {
        val result = restaurant.toUiState()
        updateState { it.copy(restaurant = result, isLoading = false, error = null) }
    }

    override fun onImagePicked(image: ByteArray) {
        updateState { it.copy(restaurant = it.restaurant.copy(image = image)) }
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

    override fun onPhoneNumberChange(phoneNumber: String) {
        val validationResult = restaurantInformationValidation.isPhoneNumberValid(phoneNumber)
        updateState {
            it.copy(
                restaurant = it.restaurant.copy(
                    phoneNumber = phoneNumber,
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
        updateState {
            it.copy(
                isLoading = true,
                restaurant = state.value.restaurant.copy(
                    isSaveButtonEnabled = false
                )
            )
        }
        tryToExecute(
            { manageRestaurantInformation.updateRestaurantInformation(restaurant) },
            ::onUpdateRestaurantInfoSuccess,
            ::onError
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

        val updatedState = state.copy(validationState = validationResult)
        updateState { it.copy(restaurant = updatedState) }
    }

    private fun onUpdateRestaurantInfoSuccess(result: Boolean) {
        updateState {
            it.copy(
                isLoading = false, error = null,
                restaurant = state.value.restaurant.copy(
                    isSaveButtonEnabled = true
                ),
                snackBarState = true
            )
        }
        sendNewEffect(RestaurantInformationUiEffect.UpdateInformationSuccess)
        showSnackBar()
    }

    private fun showSnackBar() {
        viewModelScope.launch {
            updateState { it.copy(showSnackBar = true) }
            delay(3000)
            updateState { it.copy(showSnackBar = false) }
        }
    }

    override fun onClickLogout() {
        tryToExecute(
            { logoutUser.logoutUser() },
            { onLogoutSuccess() },
            ::onError
        )
        sendNewEffect(RestaurantInformationUiEffect.LogoutSuccess)
    }

    private fun onLogoutSuccess() {
        sendNewEffect(RestaurantInformationUiEffect.LogoutSuccess)
    }

    override fun onClickBackArrow() {
        sendNewEffect(RestaurantInformationUiEffect.NavigateUp)
    }
}