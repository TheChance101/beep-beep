package presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    onStateChange: () -> Unit,
    showDropDownMenu: () -> Unit,
    state: String,
    restaurantName: String,
    isMultipleRestaurants: Boolean = false,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier.fillMaxWidth().padding(Theme.dimens.space16),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        MultipleRestaurants(
            restaurantName = restaurantName,
            showDropDownMenu = showDropDownMenu,
            isMultipleRestaurants = isMultipleRestaurants
        )

        BpButton(
            onClick = onStateChange,
            title = state
        )
    }
}


@Composable
private fun MultipleRestaurants(
    restaurantName: String,
    showDropDownMenu: () -> Unit,
    modifier: Modifier = Modifier,
    isMultipleRestaurants: Boolean = false
) {

    if (isMultipleRestaurants) {
        modifier.clickable { showDropDownMenu() }
    }

    Row(
        modifier = modifier,
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
//                painter = painterResource("drawable/arrowdown.svg"),
                imageVector = Icons.Default.Home,
                contentDescription = null
            )
        }

    }
}