package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
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
        TODO("Not yet implemented")
    }
    //region permission
    override fun onOwnerEmailChanged(ownerEmail: String) {
      updateState { it.copy(ownerEmail = ownerEmail) }
    }

    override fun onRestaurantNameChanged(restaurantName: String) {
        updateState { it.copy(restaurantName = restaurantName) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
    }

    override fun onRequestPermissionClicked() {
        TODO("Not yet implemented")
    }

    override fun onSubmitClicked(restaurantName: String, ownerEmail: String, description: String) {
        TODO("Not yet implemented")
    }

    override fun onCancelClicked() {
        TODO("Not yet implemented")
    }

    override fun onSheetBackgroundClicked() {
        TODO("Not yet implemented")
    }
    //end region
}