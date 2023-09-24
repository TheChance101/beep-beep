package presentation.map.composable


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.entity.Location

@Composable
expect fun MapView(
    modifier: Modifier = Modifier,
    currentLocation: Location,
    destination: Location? = null,
)