package presentation.PreferredFood

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import com.beepbeep.designSystem.ui.theme.Theme.colors
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.HeadFirstCard
import presentation.composable.PreferredFoodCard
import presentation.preferredRide.PreferredRideScreen
import resources.Resources


object PreferredFoodScreen :
    BaseScreen<PreferredFoodScreenModel, PreferredFoodUIState, PreferredFoodUIEffect, PreferredFoodInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: PreferredFoodUIEffect, navigator: Navigator) {
        when (effect) {
            is PreferredFoodUIEffect.NavigateToPreferredScreen -> navigator.push(PreferredRideScreen())
        }
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
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
            ) {
                LazyVerticalGrid(
                    contentPadding = PaddingValues(top = 24.dp),
                    columns = GridCells.Adaptive(144.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),

                    ) {


                    items(count = if (state.preferredFood.size > 4) 4 else state.preferredFood.size,
                        key = { index ->
                            state.preferredFood[index].name

                        }) { index ->
                        PreferredFoodCard(
                            state = state.preferredFood[index],
                            onClick = { listener.onPreferredFoodClicked(state.preferredFood[index]) },
                            modifier = Modifier.animateItemPlacement(
                                animationSpec = tween(500),
                            )
                        )
                    }
                }
            }
        }
    }


}