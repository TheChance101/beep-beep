package presentation.preferredRide

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import domain.entity.RideQuality
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.HeadFirstCard
import presentation.composable.PreferredCard
import presentation.preferredMeal.PreferredMealScreen
import resources.Resources

class PreferredRideScreen :
    BaseScreen<PreferredRideScreenModel, PreferredRideUiState, PreferredRideUiEffect, PreferredRideInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: PreferredRideUiState, listener: PreferredRideInteractionListener) {
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
                modifier = Modifier.padding(vertical = 32.dp),
                textHeader = Resources.strings.ridePreferredTitle,
                textSubHeader = Resources.strings.ridePreferredSubTitle
            ) {
                Column(
                    modifier = Modifier.padding(top = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PreferredCard(
                        onClickRideCard = listener::onClickPreferredRide,
                        painter = painterResource(Resources.images.quickRide),
                        title = Resources.strings.quickerRoutesWithHigherCosts,
                        isMeal = false,
                        quality = RideQuality.HIGH
                    )
                    PreferredCard(
                        onClickRideCard = listener::onClickPreferredRide,
                        painter = painterResource(Resources.images.slowRide),
                        title = Resources.strings.slowerRoutesWithLowCosts,
                        isMeal = false,
                        quality = RideQuality.LOW
                    )
                }
            }
        }
    }

    override fun onEffect(effect: PreferredRideUiEffect, navigator: Navigator) {
        when (effect) {
            is PreferredRideUiEffect.NavigateToPreferredMeal -> navigator.push(PreferredMealScreen())
        }
    }
}