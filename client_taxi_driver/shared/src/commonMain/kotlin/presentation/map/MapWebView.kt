package presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.entity.Location

@Composable
expect fun MapWebView(
    modifier: Modifier = Modifier,
    url: String,
    currentLocation: Location,
    destination: Location? = null,
): Unit