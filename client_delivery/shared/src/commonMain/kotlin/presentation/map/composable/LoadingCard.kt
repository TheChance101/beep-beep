package presentation.map.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoadingCard() {
    MapCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = Resources.strings.beReady,
                    style = Theme.typography.titleLarge,
                    color = Theme.colors.contentPrimary
                )
                Text(
                    text = Resources.strings.subLoadingText,
                    style = Theme.typography.caption,
                    color = Theme.colors.contentTertiary
                )
            }
            Image(
                modifier = Modifier
                    .width(80.dp)
                    .height(85.dp),
                painter = painterResource(Resources.images.readyDeliveryBike),
                contentDescription = "ready bike"
            )
        }
    }
}