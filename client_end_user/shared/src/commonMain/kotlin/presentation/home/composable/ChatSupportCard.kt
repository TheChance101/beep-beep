package presentation.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
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
fun ChatSupportCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Have Questions ?",
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Have Questions ?",
                    style = Theme.typography.title,
                    color = Theme.colors.contentPrimary
                )
                Icon(
                    painter = painterResource(Resources.images.arrowRight),
                    contentDescription = "connect with support text icon",
                    tint = Theme.colors.primary
                )
            }

        }
        Image(
            painterResource(Resources.images.chatImage),
            contentDescription = "support chat logo",
        )
    }
}