package presentation.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen
import presentation.main.MainContainer
import resources.Resources

class ProfileScreen:
    BaseScreen<ProfileScreenModel, ProfileUIState, ProfileUIEffect, ProfileInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: ProfileUIEffect, navigator: Navigator) {
        when(effect){
            is ProfileUIEffect.Logout -> navigator.replaceAll(MainContainer)
        }
    }


    @Composable
    override fun onRender(state: ProfileUIState, listener: ProfileInteractionListener) {
        TODO("Not yet implemented")
    }


}