package presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.compose.getKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val navigate = LocalNavigator.currentOrThrow
        val screenModel: MainScreenModel = getScreenModel()
        val state by screenModel.state.collectAsState()

        MainScreenContent(state = state, onClickWelcome = { navigate.pop() })
    }

    @Composable
    private fun MainScreenContent(
        state: MainScreenUIState,
        onClickWelcome: () -> Unit,
    ) {
        Column(
            Modifier.fillMaxSize().background(Theme.colors.secondary),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                state.welcomeMessage,
                style = Theme.typography.headlineLarge,
                modifier = Modifier.clickable { onClickWelcome() }
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