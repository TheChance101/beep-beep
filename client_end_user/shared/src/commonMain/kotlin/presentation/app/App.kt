package presentation.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.koin.compose.koinInject
import presentation.main.MainContainer
import presentation.preferredMeal.PreferredMealScreen
import resources.BeepBeepTheme
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    BeepBeepTheme {

        val appScreenModel = koinInject<AppScreenModel>()
        val isFirstTime = remember { mutableStateOf(false) }

        LaunchedEffect(key1 = Unit) {
            isFirstTime.value =  appScreenModel.getInitScreen()
        }

        if(isFirstTime.value){
            Navigator(PreferredMealScreen()) {
                SlideTransition(it)
            }

        }else{
            Navigator(MainContainer) {
                SlideTransition(it)
            }
        }
    }
}


