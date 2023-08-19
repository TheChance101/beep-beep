package presentation.screens.signup

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignUpScreen : Screen, KoinComponent {

    val signUpScreenModel : SignUpScreenModel by inject()
    @Composable
    override fun Content() {


    }

}

@Composable
private fun SignUpScreenContent(){

}