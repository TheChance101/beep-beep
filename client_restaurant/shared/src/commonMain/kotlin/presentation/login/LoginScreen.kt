package presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.base.BaseScreen
import presentation.restaurantSelection.RestaurantSelectionScreen

class LoginScreen :
    BaseScreen<LoginScreenModel, LoginScreenUIState, LoginScreenUIEffect, LoginScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: LoginScreenUIEffect, navigator: Navigator) {
        when (effect) {
            is LoginScreenUIEffect.Login -> navigator.push(RestaurantSelectionScreen(effect.ownerId))
            else -> {}
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun onRender(state: LoginScreenUIState, listener: LoginScreenInteractionListener) {
        Column(
            Modifier.fillMaxSize().background(Theme.colors.secondary),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Login",
                style = Theme.typography.headlineLarge,
            )
            BpTextField(
                text = state.email,
                onValueChange = listener::onEmailChanged,
                label = "Username",
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
                errorMessage = state.error,
            )
            BpTextField(
                text = state.password,
                onValueChange = listener::onPasswordChanged,
                label = "Password",
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth(),
                errorMessage = state.error,
            )
            BpButton(
                onClick = listener::onClickLogin,
                title = "Login",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
