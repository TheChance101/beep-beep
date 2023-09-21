package presentation.map.composable


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun MapView(
    modifier: Modifier = Modifier,
    currentLocation: Pair<Double, Double>,
    destination: Pair<Double, Double>?
)