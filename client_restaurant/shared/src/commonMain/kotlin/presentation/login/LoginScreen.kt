package presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.compose.getKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import presentation.main.MainScreen

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigate = LocalNavigator.currentOrThrow
        val screenModel: LoginScreenModel = getScreenModel()
        val state by screenModel.state.collectAsState()

        LoginScreenContent(
            state = state,
            onClickLogin = { navigate.push(MainScreen()) },
            onEmailChanged = screenModel::onEmailChanged,
            onPasswordChanged = screenModel::onPasswordChanged,
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun LoginScreenContent(
        state: LoginScreenUIState,
        onClickLogin: () -> Unit,
        onEmailChanged: (String) -> Unit,
        onPasswordChanged: (String) -> Unit,
    ) {
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
                onValueChange = onEmailChanged,
                label = "Username",
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
                errorMessage = state.error,
            )
            BpTextField(
                text = state.password,
                onValueChange = onPasswordChanged,
                label = "Password",
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth(),
                errorMessage = state.error,
            )
            BpButton(
                onClick = onClickLogin,
                title = "Login",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }


    @Composable
    inline fun <reified T : ScreenModel> Screen.getScreenModel(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null,
    ): T {
        val koin = getKoin()
        return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
    }
}