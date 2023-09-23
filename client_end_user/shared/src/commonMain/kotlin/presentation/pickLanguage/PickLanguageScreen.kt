package presentation.pickLanguage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.preferredFood.PreferredFoodScreen
import presentation.base.BaseScreen
import presentation.composable.LanguageCard
import presentation.composable.HeadFirstCard
import resources.Resources

object PickLanguageScreen :
    BaseScreen<PickLanguageScreenModel, PickLanguageUIState, PickLanguageUIEffect, PickLanguageInteractionListener>() {
    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: PickLanguageUIEffect, navigator: Navigator) {
        when (effect) {
            is PickLanguageUIEffect.onGoToPreferredLanguage -> navigator.push(PreferredFoodScreen)
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: PickLanguageUIState, listener: PickLanguageInteractionListener) {
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
                textHeader = Resources.strings.languageAskAboutLanguage,
                textSubHeader = Resources.strings.selectLanguage
            ) {
                LazyVerticalGrid(
                    contentPadding = PaddingValues(top = 16.dp),
                    columns = GridCells.Adaptive(140.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),

                    ) {
                    items(state.languages.size) { index ->
                        LanguageCard(
                            state = state.languages[index],
                            onClick = { listener.onLanguageSelected(state.languages[index]) }
                        )
                    }
                }
            }
        }
    }
}
