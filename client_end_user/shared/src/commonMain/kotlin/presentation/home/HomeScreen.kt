package presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.base.BaseScreen
import presentation.composable.SectionHeader
import presentation.home.composable.CuisineCard
import resources.Resources

class HomeScreen :
    BaseScreen<HomeScreenModel, HomeScreenUiState, HomeScreenUiEffect, HomeScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: HomeScreenUiEffect, navigator: Navigator) {
        when (effect) {
            is HomeScreenUiEffect.CuisineUiEffect -> println("Cuisine id ${effect.cuisineId}")
            is HomeScreenUiEffect.SeeAllCuisineUiEffect -> println("Navigate to Cuisine Screen")
        }
    }

    @Composable
    override fun onRender(state: HomeScreenUiState, listener: HomeScreenInteractionListener) {
        Column(modifier = Modifier.fillMaxSize().background(Theme.colors.background)) {

            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SectionHeader(
                    onClickViewAll = listener::onclickSeeAllCuisines,
                    title = Resources.strings.cuisineSectionTitle,
                    showViewAll = true
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.popularCuisines.forEach { cuisine ->
                        CuisineCard(
                            modifier = Modifier,
                            cuisine = cuisine,
                            onClickCuisine = listener::onClickCuisineItem
                        )
                    }
                }
            }
        }
    }
}