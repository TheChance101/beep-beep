package presentation.screens.login

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginScreen : Screen, KoinComponent {

    val loginScreenModel : LoginScreenModel by inject()
    @Composable
    override fun Content() {


    }

}

@Composable
private fun LoginScreenContent(){

}