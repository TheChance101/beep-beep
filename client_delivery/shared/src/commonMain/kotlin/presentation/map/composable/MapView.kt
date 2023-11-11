package presentation.map.composable


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.mohamedrejeb.calf.ui.web.WebView
import com.mohamedrejeb.calf.ui.web.rememberWebViewState
import presentation.map.LocationUiState

@Composable
fun MapView(
    deliveryLocation: LocationUiState,
    destination: LocationUiState,
    modifier: Modifier = Modifier,
    ) {
    val state = rememberWebViewState(url = MAP_URL)
    state.settings.javaScriptEnabled = true

    LaunchedEffect(state.isLoading) {
        // Get the current loading state
    }

    AnimatedVisibility(false) {
        LaunchedEffect(true) {
            val jsCode = """
                GetMap();
                clearDirections();
            """.trimIndent()

            state.evaluateJavascript(jsCode, null)
        }
    }

    LaunchedEffect(key1 = deliveryLocation) {
        destination.let { location ->
            state.evaluateJavascript("clearDirections()", null)
            state.evaluateJavascript(
                "getDirections(${deliveryLocation.lat},${deliveryLocation.lng},${location.lat},${location.lng})",
                null
            )
        }
    }

    WebView(
        state = state,
        modifier = modifier.fillMaxSize()
    )
}

private const val MAP_URL = "File:///android_asset/bing_map/map/index.html"
