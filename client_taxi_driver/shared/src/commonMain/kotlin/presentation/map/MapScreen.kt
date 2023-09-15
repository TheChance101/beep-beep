package presentation.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.resources.Resources

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
class MapScreen :
    BaseScreen<MapScreenModel, MapScreenUiState, MapUiEffect, MapScreenInteractionListener>() {

    @Composable
    override fun onRender(state: MapScreenUiState, listener: MapScreenInteractionListener) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            BpAppBar(
                isBackIconVisible = false,
                title = Resources.strings.welcomeDriver
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .width(40.dp)
                        .height(40.dp)
                        .background(
                            color = Theme.colors.surface,
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(Resources.images.close),
                        contentDescription = Resources.strings.close,
                        tint = Theme.colors.contentPrimary
                    )
                }
            }
            NewOrder(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }

    override fun onEffect(effect: MapUiEffect, navigator: Navigator) {

    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }


    @Composable
    private fun FindingRideCard(
        modifier: Modifier = Modifier,
    ) {
        Card(
            modifier = modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 21.dp),
            colors = CardDefaults.cardColors(containerColor = Theme.colors.surface),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(0.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = Resources.strings.mapScreenFindingRequest,
                    color = Theme.colors.contentPrimary,
                    style = Theme.typography.headline,
                )

                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 18.dp),
                    color = Theme.colors.primary,
                    trackColor = Theme.colors.secondary,
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }


    @Composable
    private fun NewOrder(
        modifier: Modifier = Modifier,
    ) {
        Card(
            modifier = modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Theme.colors.surface),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(0.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Text(
                    text = Resources.strings.newOrder,
                    color = Theme.colors.contentPrimary,
                    style = Theme.typography.headline,
                )

                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = Resources.strings.name,
                    color = Theme.colors.contentPrimary,
                    style = Theme.typography.titleLarge,
                )

                Row(modifier = Modifier.padding(top = 10.dp)) {
                    Image(
                        painter = painterResource(Resources.images.mapPoint),
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = Resources.strings.name,
                        color = Theme.colors.contentSecondary,
                        style = Theme.typography.body,
                    )
                }

                BpButton(
                    modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                    title = Resources.strings.acceptRequest,
                    onClick = {})

                BpButton(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    title = Resources.strings.cancel,
                    containerColor = Color.Transparent,
                    contentColor = Theme.colors.contentTertiary,
                    onClick = {})
            }
        }
    }


    @Composable
    private fun Order(
        modifier: Modifier = Modifier,
    ) {
        Card(
            modifier = modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Theme.colors.surface),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(0.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Text(
                    text = Resources.strings.name,
                    color = Theme.colors.contentPrimary,
                    style = Theme.typography.headline,
                )

                Row(modifier = Modifier.padding(top = 30.dp)) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)

                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = Resources.strings.pickUp,
                            color = Theme.colors.contentSecondary,
                            style = Theme.typography.titleMedium,
                        )
                        Text(
                            text = Resources.strings.name,
                            color = Theme.colors.contentSecondary,
                            style = Theme.typography.title,
                        )
                    }
                }

                Row(modifier = Modifier.padding(top = 24.dp)) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)

                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = Resources.strings.dropOff,
                            color = Theme.colors.contentSecondary,
                            style = Theme.typography.titleMedium,
                        )
                        Text(
                            text = Resources.strings.name,
                            color = Theme.colors.contentSecondary,
                            style = Theme.typography.title,
                        )
                    }
                }

                BpButton(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    title = Resources.strings.arrived,
                    onClick = {})
            }
        }
    }
}
