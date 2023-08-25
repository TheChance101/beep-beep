package presentation.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composable.modifier.noRippleEffect
import presentation.restaurantSelection.RestaurantUIState
import resources.Resources

@Composable
fun HomeAppBar(
    onRestaurantSelect: (String) -> Unit,
    onShowMenu: () -> Unit,
    onDismissMenu: () -> Unit,
    state: Boolean,
    restaurantName: String,
    expanded: Boolean,
    restaurants: List<RestaurantUIState>,
    modifier: Modifier = Modifier,
) {
    val buttonBackgroundColor by animateColorAsState(if (state) Theme.colors.hover else Color.Transparent)
    val buttonContentColor by animateColorAsState(if (state) Theme.colors.primary else Theme.colors.disable)

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.fillMaxWidth().padding(Theme.dimens.space16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            MultipleRestaurants(
                onClick = onShowMenu,
                restaurantName = restaurantName,
                isMultipleRestaurants = restaurants.isNotEmpty()
            )

            BpTransparentButton(
                modifier = Modifier.background(buttonBackgroundColor),
                title = if (state) Resources.strings.open else Resources.strings.closed,
                enabled = false,
                contentColor = buttonContentColor,
                onClick = {}
            )
        }

        BpDropdownMenu(
            expanded = expanded,
            modifier = Modifier.heightIn(max = 350.dp)
                .widthIn(min = 260.dp),
            offset = DpOffset(Theme.dimens.space16, 0.dp),
            onDismissRequest = onDismissMenu
        ) {
            restaurants.forEach { restaurant ->
                RestaurantInformation(
                    onRestaurantClick = { onRestaurantSelect(restaurant.id) },
                    restaurantName = restaurant.restaurantName,
                    restaurantNumber = restaurant.restaurantNumber,
                    isOpen = restaurant.isOpen
                )
            }
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun MultipleRestaurants(
    onClick: () -> Unit,
    restaurantName: String,
    modifier: Modifier = Modifier,
    isMultipleRestaurants: Boolean
) {
    Row(
        modifier = modifier.noRippleEffect(onClick),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = restaurantName,
            style = Theme.typography.titleLarge,
            color = Theme.colors.contentPrimary
        )

        if (isMultipleRestaurants) {
            Icon(
                painter = painterResource(Resources.images.arrowDown),
                contentDescription = null
            )
        }

    }
}

