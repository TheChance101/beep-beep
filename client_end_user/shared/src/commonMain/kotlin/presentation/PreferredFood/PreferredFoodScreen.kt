package presentation.PreferredFood

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.HeadFirstCard
import resources.Resources


object PreferredFoodScreen : BaseScreen<PreferredFoodScreenModel,PreferredFoodUIState, PreferredFoodUIEffect, PreferredFoodInteractionListener>() {

    @Composable
    override fun Content() {
       initScreen(getScreenModel())
    }

    override fun onEffect(effect: PreferredFoodUIEffect, navigator: Navigator) {
        when(effect){
            is PreferredFoodUIEffect.NavigateToPreferredScreen -> {}
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: PreferredFoodUIState, listener: PreferredFoodInteractionListener) {
        Box(
            modifier = Modifier.fillMaxSize().background(Theme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Resources.images.backgroundPattern),
                contentDescription = Resources.strings.backgroundDescription,
                contentScale = ContentScale.Crop
            )
            HeadFirstCard(
                textHeader = Resources.strings.loginWelcomeMessage,
                textSubHeader = Resources.strings.loginSubWelcomeMessage
            ) {}}
    }



}