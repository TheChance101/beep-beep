package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.ILoginUserUseCase
import domain.usecase.IManageRestaurantInformationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class LoginScreenModel(
    private val loginUserUseCase: ILoginUserUseCase,
    private val manageRestaurant: IManageRestaurantInformationUseCase,
) :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope


    private fun getRestaurants() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { manageRestaurant.getOwnerRestaurants() },
            ::onGetRestaurantsSuccess,
            ::onError
        )
    }

    private fun onGetRestaurantsSuccess(restaurants: List<Restaurant>) {
        val hasMultipleRestaurants = restaurants.size > 1
        if (hasMultipleRestaurants) {
            sendNewEffect(LoginScreenUIEffect.OnNavigateToRestaurantScreenSelection)
        } else {
            sendNewEffect(LoginScreenUIEffect.OnNavigateToMainScreen)
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(isLoading = false, error = errorState) }
    }

    override fun onUserNameChanged(userName: String) {
        updateState { it.copy(userName = userName, isUsernameError = false) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password, isPasswordError = false) }
    }

    override fun onKeepLoggedInClicked() {
        updateState { it.copy(keepLoggedIn = !it.keepLoggedIn) }
    }

    override fun onClickLogin(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean,
    ) {
        updateState { it.copy(isLoading = true, noInternetConnection = false, isEnable = false) }
        tryToExecute(
            { loginUserUseCase.loginUser(userName, password, isKeepMeLoggedInChecked) },
            { onLoginSuccess() },
            this::onLoginFailed
        )
    }

    private fun onLoginSuccess() {
        updateState {
            it.copy(
                isLoading = false,
                isSuccess = true,
                noInternetConnection = false,
                isCredentialsError = false,
                usernameErrorMsg = "",
                passwordErrorMsg = ""
            )
        }

        getRestaurants()
    }

    private fun onLoginFailed(error: ErrorState) {
        updateState { it.copy(isLoading = false, error = error, isEnable = true) }
        handleErrorState(error)
    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            ErrorState.NoInternet -> {
                updateState { it.copy(noInternetConnection = true) }
            }
            ErrorState.RequestFailed -> {}
            ErrorState.UnAuthorized -> {}
            ErrorState.HasNoPermission -> {
                state.value.sheetState.show()
            }

            is ErrorState.UnknownError -> {}
            is ErrorState.InvalidCredentials -> {
                updateState {
                    it.copy(
                        isPasswordError = true,
                        isUsernameError = true,
                        isCredentialsError = true
                    )
                }
            }

            is ErrorState.UserNotExist -> {
                updateState {
                    it.copy(
                        usernameErrorMsg = error.errorMessage,
                        isUsernameError = true,
                        isCredentialsError = false
                    )
                }
            }

            is ErrorState.WrongPassword -> {
                updateState {
                    it.copy(
                        passwordErrorMsg = error.errorMessage,
                        isPasswordError = true,
                        isCredentialsError = false
                    )
                }
            }
        }
    }

    // region permission
    override fun onRestaurantNameChanged(restaurantName: String) {
        updateState { it.copy(restaurantName = restaurantName) }
    }

    override fun onOwnerEmailChanged(ownerEmail: String) {
        updateState { it.copy(ownerEmail = ownerEmail) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
    }

    override fun onRequestPermissionClicked() {
        showPermissionSheetWithDelay()

    }

    private fun showPermissionSheetWithDelay() {
        coroutineScope.launch {
            state.value.sheetState.dismiss()
            delay(300)
            updateState { it.copy(showPermissionSheet = true) }
            state.value.sheetState.show()
        }
    }

    override fun onSubmitClicked(
        restaurantName: String,
        ownerEmail: String,
        description: String,
    ) {
        tryToExecute(
            {
                loginUserUseCase.requestPermission(
                    restaurantName,
                    ownerEmail,
                    description
                )
            },
            { onAskForPermissionSuccess() },
            ::onAskForPermissionFailed
        )
    }

    private fun onAskForPermissionSuccess() {
        state.value.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
        // todo send effect for that to show toast or something
    }


    private fun onAskForPermissionFailed(error: ErrorState) {
        state.value.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
        // todo send effect for that to show toast or something
    }

    override fun onCancelClicked() {
        state.value.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
    }

    override fun onSheetBackgroundClicked() {
        state.value.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
    }

    private suspend fun delayAndChangePermissionSheetState(show: Boolean) {
        delay(300)
        updateState { it.copy(showPermissionSheet = show) }
    }
    // endregion
}
