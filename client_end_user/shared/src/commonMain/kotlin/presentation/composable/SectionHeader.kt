package presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    showViewAll: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = Theme.typography.titleLarge, color = Theme.colors.contentPrimary)

        if (showViewAll) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Resources.strings.viewAll,
                    style = Theme.typography.caption,
                    color = Theme.colors.primary
                )
                Icon(
                    painter = painterResource(Resources.images.seeAll),
                    contentDescription = Resources.strings.seeAllDescription,
                    tint = Theme.colors.primary
                )
            }
        }

    }
}