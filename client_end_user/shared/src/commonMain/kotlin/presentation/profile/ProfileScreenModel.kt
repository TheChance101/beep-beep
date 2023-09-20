package presentation.profile

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.main.MainScreenUiEffect

class ProfileScreenModel :BaseScreenModel<ProfileUIState, ProfileUIEffect >(ProfileUIState()) ,ProfileInteractionListener{
    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onFullNameChanged(username: String) {
        TODO("Not yet implemented")
    }

    override fun onPhoneNumberChanged(phone: String) {
        TODO("Not yet implemented")
    }

    override fun onSaveProfileInfo() {
        TODO("Not yet implemented")
    }

    override fun onLogout() {
        sendNewEffect(ProfileUIEffect.Logout)
    }
}