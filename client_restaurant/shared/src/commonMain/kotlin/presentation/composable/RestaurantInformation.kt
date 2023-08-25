package presentation.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.composable.modifier.noRippleEffect
import resources.Resources

@Composable
fun RestaurantInformation(
    onRestaurantClick: () -> Unit,
    restaurantName: String,
    restaurantNumber: String,
    isOpen: Boolean,
    modifier: Modifier = Modifier
) {
    val buttonBackgroundColor by animateColorAsState(if (isOpen) Theme.colors.hover else Color.Transparent)
    val buttonContentColor by animateColorAsState(if (isOpen) Theme.colors.primary else Theme.colors.disable)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .noRippleEffect(onRestaurantClick)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = restaurantName,
                style = Theme.typography.title.copy(Theme.colors.contentPrimary)
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = restaurantNumber,
                style = Theme.typography.caption.copy(Theme.colors.contentTertiary)
            )
        }

        BpTransparentButton(
            modifier = Modifier.background(buttonBackgroundColor),
            title = if (isOpen) Resources.strings.open else Resources.strings.closed,
            enabled = false,
            contentColor = buttonContentColor,
            onClick = {}
        )
    }

}
