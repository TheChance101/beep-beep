package presentation.login

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen

class LoginScreen : BaseScreen<
        LoginScreenModel,
        LoginScreenUIState,
        LoginScreenUIEffect,
        LoginScreenInteractionListener>() {

    @Composable
    override fun onRender(state: LoginScreenUIState, listener: LoginScreenInteractionListener) {
        TODO("Not yet implemented")
    }

    override fun onEffect(effect: LoginScreenUIEffect, navigator: Navigator) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

}