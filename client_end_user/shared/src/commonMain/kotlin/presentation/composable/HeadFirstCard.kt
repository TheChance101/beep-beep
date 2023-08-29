package presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@Composable
fun HeadFirstCard(
    textHeader: String,
    textSubHeader: String,
    modifier: Modifier = Modifier,
    logo: String = Resources.images.bpLogo,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp)
            .clip(shape = RoundedCornerShape(Theme.radius.medium))
            .background(Theme.colors.surface)
            .padding(16.dp)
    ) {
        CardHeader(textHeader = textHeader, textSubHeader = textSubHeader, logo = logo)
        content()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CardHeader(
    textHeader: String,
    textSubHeader: String,
    logo: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(32.dp)) {
        Icon(
            modifier = Modifier.size(width = 70.dp, height = 32.dp),
            painter = painterResource(logo),
            contentDescription = logo,
            tint = Theme.colors.primary
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = textHeader,
                style = Theme.typography.titleLarge,
                color = Theme.colors.contentPrimary
            )
            Text(
                text = textSubHeader,
                style = Theme.typography.body,
                color = Theme.colors.contentTertiary
            )
        }
    }
}