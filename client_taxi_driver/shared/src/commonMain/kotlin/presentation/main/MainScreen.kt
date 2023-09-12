package presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.resources.Resources

class MainScreen :
    BaseScreen<MainScreenModel, MainScreenUiState, MainUiEffect, MainScreenInteractionListener>() {

    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun onRender(state: MainScreenUiState, listener: MainScreenInteractionListener) {
        Box(
            modifier = Modifier.fillMaxSize().background(Theme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Resources.images.backgroundPattern),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Theme.colors.surface, RoundedCornerShape(8.dp))
                        .padding(horizontal = 14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painterResource(Resources.images.mainScreenImage),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = Resources.strings.mainScreenTitle,
                        color = Theme.colors.contentPrimary,
                        style = Theme.typography.titleLarge,
                    )

                    Text(
                        text = Resources.strings.mainScreenSubTitle,
                        color = Theme.colors.contentTertiary,
                        style = Theme.typography.body,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    BpButton(
                        title = Resources.strings.start,
                        onClick = {},
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 22.dp)
                    )
                }
            }
        }
    }

    override fun onEffect(effect: MainUiEffect, navigator: Navigator) {

    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

}
