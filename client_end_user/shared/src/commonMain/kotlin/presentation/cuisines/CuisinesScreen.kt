package presentation.cuisines

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.composable.modifier.shimmerEffect
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.home.composable.CuisineCard
import presentation.meals.MealsScreen
import resources.Resources

class CuisinesScreen :
    BaseScreen<CuisinesScreenModel, CuisinesUiState, CuisinesUiEffect, CuisinesInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: CuisinesUiEffect, navigator: Navigator) {
        when (effect) {
            is CuisinesUiEffect.NavigateToCuisineDetails -> {
                navigator.push(MealsScreen(effect.cuisineId, effect.cuisineName))
            }

            CuisinesUiEffect.NavigateBack -> navigator.pop()
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: CuisinesUiState, listener: CuisinesInteractionListener) {
        Column(Modifier.fillMaxSize().background(Theme.colors.background)) {
            BpAppBar(
                title = Resources.strings.allCuisines,
                onNavigateUp = listener::onBackIconClicked,
                painterResource = painterResource(Resources.images.arrowLeft)
            )
            AnimatedContent(state.isLoading) {
                if (state.isLoading) {
                    LoadingCuisines()
                } else {
                    CuisinesContent(state, listener)
                }
            }
        }
    }
}

@Composable
fun CuisinesContent(
    state: CuisinesUiState,
    listener: CuisinesInteractionListener
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        columns = GridCells.Adaptive(96.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(state.cuisines) { cuisine ->
            CuisineCard(
                cuisine = cuisine,
                onClickCuisine = listener::onCuisineClicked,
                width = 104.dp,
                imagePadding = PaddingValues(vertical = 24.dp),
                backGroundColor = Theme.colors.surface
            )
        }
    }
}

@Composable
fun LoadingCuisines() {
    LazyVerticalGrid(
        contentPadding = PaddingValues(24.dp),
        columns = GridCells.Adaptive(96.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(10) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .clip(shape = RoundedCornerShape(Theme.radius.medium))
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .padding(top = 8.dp)
                        .shimmerEffect()
                )
            }
        }
    }
}
