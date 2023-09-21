package presentation.profile

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.UserDetails
import domain.usecase.IManageUserUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.main.MainScreenUiEffect

class ProfileScreenModel(
  private val  manageUser: IManageUserUseCase
) :BaseScreenModel<ProfileUIState, ProfileUIEffect >(ProfileUIState()) ,ProfileInteractionListener{

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getUserProfile()
    }

    private fun getUserProfile () {
        tryToExecute(
            { manageUser.getUserProfile() } ,

            ::onGetUserProfileSuccess,
            ::onGetUserProfileError
        )
    }

    private fun onGetUserProfileSuccess (userDetails: UserDetails) {
        val result= userDetails.toUIState()
        updateState { it.copy(user = result, fullName = result.fullName, phoneNumber = result.phoneNumber ) }
    }

    private fun onGetUserProfileError (errorState: ErrorState) {
        println(errorState)
    }

    override fun onFullNameChanged(username: String) {
        updateState { it.copy(fullName = username,isButtonEnabled = true,) }
    }

    override fun onPhoneNumberChanged(phone: String) {
        updateState { it.copy(phoneNumber = phone,isButtonEnabled = true,) }
    }

    override fun onSaveProfileInfo() {
        updateState { it.copy(isButtonEnabled = true) }

    }

    override fun onLogout() {
        sendNewEffect(ProfileUIEffect.Logout)
    }
}