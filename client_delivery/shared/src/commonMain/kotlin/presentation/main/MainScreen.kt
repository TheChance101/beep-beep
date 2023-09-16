package presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.map.MapScreen
import resources.Resources

class MainScreen :
    BaseScreen<MainScreenModel, MainUiState, MainScreenUiEffect, MainInteractionListener>() {
    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: MainScreenUiEffect, navigator: Navigator) {
        when (effect) {
            MainScreenUiEffect.Start -> navigator.push(MapScreen())
        }
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun onRender(state: MainUiState, listener: MainInteractionListener) {
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
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
                    .clip(shape = RoundedCornerShape(Theme.radius.medium))
                    .background(Theme.colors.surface)
                    .padding(top = 42.dp, start = 14.dp, end = 14.dp, bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(Resources.images.deliveryBike),
                    contentDescription = Resources.strings.backgroundDescription,
                    contentScale = ContentScale.FillHeight
                )
                Text(
                    text = Resources.strings.foodsAreWaiting,
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = Theme.typography.titleLarge,
                    color = Theme.colors.contentPrimary
                )
                Text(
                    text = Resources.strings.tapStartTitle,
                    style = Theme.typography.body,
                    textAlign = TextAlign.Center,
                    color = Theme.colors.contentSecondary
                )
                BpButton(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    title = Resources.strings.start,
                    onClick = listener::onClickStart,
                    enabled = !state.isLoading
                )
            }
        }
    }
}