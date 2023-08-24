package presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.theme.Theme

//ToDo: In progress
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    onStateChange: () -> Unit,
    showDropDownMenu: () -> Unit,
    onDismissRequest: () -> Unit,
    state: String,
    expanded: Boolean,
    restaurantName: String,
    isMultipleRestaurants: Boolean = false,
    modifier: Modifier = Modifier,
) {

    Column {
        Row(
            modifier = modifier.fillMaxWidth().padding(Theme.dimens.space16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            MultipleRestaurants(
                modifier = if (isMultipleRestaurants) {
                    Modifier.clickable { showDropDownMenu() }
                } else {
                    Modifier
                },
                restaurantName = restaurantName,
                isMultipleRestaurants = isMultipleRestaurants
            )

            BpButton(
                onClick = onStateChange,
                title = state
            )
        }

        BpDropdownMenu(
            expanded = expanded,
            offset = DpOffset(Theme.dimens.space16, 0.dp),
            onDismissRequest = onDismissRequest
        ) {

            val modifier = Modifier.padding(20.dp)
            Text("Option 1", modifier)
            Text("Option 2", modifier)
            Text("Option 3", modifier)
        }
    }

}


@Composable
private fun MultipleRestaurants(
    restaurantName: String,
    modifier: Modifier = Modifier,
    isMultipleRestaurants: Boolean = false
) {
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