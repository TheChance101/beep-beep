package presentation.map.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun MapView(
    modifier: Modifier,
    currentLocation: Pair<Double, Double>,
    destination: Pair<Double, Double>?
) {}