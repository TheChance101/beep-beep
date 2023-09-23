package presentation.cuisines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.home.composable.CuisineCard
import resources.Resources

class CuisinesScreen : BaseScreen<CuisinesScreenModel, CuisinesUiState,
        CuisinesUiEffect, CuisinesInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: CuisinesUiState, listener: CuisinesInteractionListener) {
        Column(Modifier.fillMaxSize().background(Theme.colors.background)) {
            BpAppBar(
                title = Resources.strings.allCuisines,
                onNavigateUp = { listener.onBackIconClicked() },
                painterResource = painterResource(Resources.images.arrowLeft)
            )
            LazyVerticalGrid(
                contentPadding = PaddingValues(24.dp),
                columns = GridCells.Adaptive(104.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(state.cuisines) { cuisine ->
                    CuisineCard(
                        cuisine = cuisine,
                        onClickCuisine = { listener.onCuisineClicked(it) },
                        width = 104.dp,
                        imagePadding = PaddingValues(vertical = 24.dp),
                        backGroundColor = Theme.colors.surface
                    )
                }
            }
        }
    }

    override fun onEffect(effect: CuisinesUiEffect, navigator: Navigator) {
        when (effect) {
            is CuisinesUiEffect.NavigateToCuisineDetails -> {
                // todo: implement CuisineDetailsScreen
//                navigator.push(CuisineDetailsScreen(effect.cuisineId))
            }

            CuisinesUiEffect.NavigateBack -> navigator.pop()
        }
    }
}

