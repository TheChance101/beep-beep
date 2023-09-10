package presentation.preferredMeal.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.composable.BpImageLoader

@Composable
fun PreferredCard(
    imageUrl: String,
    title: String,
    priceLevel: String? = null,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(Theme.radius.medium))
            .border(
                width = 1.dp,
                color = Theme.colors.divider,
                shape = RoundedCornerShape(Theme.radius.medium)
            )
            .background(Theme.colors.surface)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BpImageLoader(imageUrl, modifier.size(56.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = Theme.typography.body,
                color = Theme.colors.contentSecondary
            )
            priceLevel?.let {
                Text(
                    text = priceLevel,
                    style = Theme.typography.titleMedium,
                    color = Theme.colors.contentPrimary
                )
            }

        }
    }
}