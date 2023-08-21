package presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.compose.koinInject
import presentation.base.BaseScreen

class MainScreen :
    BaseScreen<MainScreenModel, MainScreenUIState, MainScreenUIEffect, MainScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(koinInject ())
    }

    @Composable
    override fun onRender(state: MainScreenUIState, listener: MainScreenInteractionListener) {
        Column(
            Modifier.fillMaxSize().background(Theme.colors.secondary),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                state.welcomeMessage,
                style = Theme.typography.headlineLarge,
                modifier = Modifier.clickable { listener.onClickBack() }
            )
        }
    }

    override fun onEffect(effect: MainScreenUIEffect, navigator: Navigator) {
        when (effect) {
            is MainScreenUIEffect.Back -> navigator.pop()
        }
    }
}