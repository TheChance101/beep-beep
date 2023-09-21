package presentation.profile

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.main.MainScreenUiEffect

class ProfileScreenModel :BaseScreenModel<ProfileUIState, ProfileUIEffect >(ProfileUIState()) ,ProfileInteractionListener{
    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onFullNameChanged(username: String) {
        updateState { it.copy(fullName = username) }
    }

    override fun onPhoneNumberChanged(phone: String) {
        updateState { it.copy(phoneNumber = phone) }
    }

    override fun onSaveProfileInfo() {

    }

    override fun onLogout() {
        sendNewEffect(ProfileUIEffect.Logout)
    }
}