package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel

class LoginScreenModel :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
LoginScreenInteractionListener{

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    override fun onUserNameChanged(userName: String) {
        updateState { it.copy(userName = userName) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    override fun onKeepLoggedInClicked() {
        updateState { it.copy(keepLoggedIn = !it.keepLoggedIn) }
    }

    override fun onClickLogin(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean
    ) {
        state.value.sheetState.show()//fake scenario just 4 testing
    }
    //region permission
    override fun onOwnerEmailChanged(ownerEmail: String) {
      updateState { it.copy(ownerEmail = ownerEmail) }
    }

    override fun onRestaurantNameChanged(restaurantName: String) {
        updateState { it.copy(deliveryUsername = restaurantName) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
    }

    override fun onRequestPermissionClicked() {
        dismissBottomSheet()
        showPermissionSheetWithDelay()
    }

    private fun showPermissionSheetWithDelay() {
        coroutineScope.launch {
            delay(300)
            updateState { it.copy(showPermissionSheet = true) }
            state.value.sheetState.show()
        }
    }

    override fun onSubmitClicked(restaurantName: String, ownerEmail: String, description: String) {
        sendNewEffect(LoginScreenUIEffect.LoginEffect(""))//fake scenario just 4 testing
    }

    override fun onCancelClicked() {
        dismissBottomSheet()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
    }

    override fun onSheetBackgroundClicked() {
        dismissBottomSheet()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
    }

    private fun dismissBottomSheet() {
        state.value.sheetState.dismiss()
    }

    private suspend fun delayAndChangePermissionSheetState(show: Boolean) {
        delay(300)
        updateState { it.copy(showPermissionSheet = show) }
    }
    //end region
}