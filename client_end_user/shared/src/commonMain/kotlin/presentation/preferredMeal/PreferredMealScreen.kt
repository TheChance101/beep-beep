package presentation.preferredMeal

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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.HeadFirstCard
import presentation.composable.PreferredCard
import presentation.main.MainContainer
import resources.Resources

class PreferredMealScreen :
    BaseScreen<PreferredScreenModel, PreferredScreenUiState, PreferredScreenUiEffect, PreferredScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: PreferredScreenUiEffect, navigator: Navigator) {
        when (effect) {
            PreferredScreenUiEffect.NavigateToHomeScreen -> navigator.replaceAll(MainContainer)
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(
        state: PreferredScreenUiState,
        listener: PreferredScreenInteractionListener
    ) {
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
                textHeader = Resources.strings.dishPreferredTitle,
                textSubHeader = Resources.strings.dishPreferredSubTitle
            ) {
                Column(
                    modifier = Modifier.padding(top = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PreferredCard(
                        onClickMealCard = listener::onClickPreferredMeal,
                        painter = painterResource(Resources.images.wishDishLow),
                        title = Resources.strings.lowLevelDishName,
                        isMeal = true,
                        priceLevel = Resources.strings.lowPriceLevel
                    )
                    PreferredCard(
                        onClickMealCard = listener::onClickPreferredMeal,
                        painter = painterResource(Resources.images.wishDishMedium),
                        title = Resources.strings.mediumLevelDishName,
                        isMeal = true,
                        priceLevel = Resources.strings.mediumPriceLevel,
                    )
                    PreferredCard(
                        onClickMealCard = listener::onClickPreferredMeal,
                        painter = painterResource(Resources.images.wishDishHigh),
                        title = Resources.strings.highLevelDishName,
                        isMeal = true,
                        priceLevel = Resources.strings.highPriceLevel
                    )
                }

            }

        }
    }
}