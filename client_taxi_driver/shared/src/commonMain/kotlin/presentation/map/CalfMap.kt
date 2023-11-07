package presentation.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.mohamedrejeb.calf.ui.web.WebView
import com.mohamedrejeb.calf.ui.web.rememberWebViewState
import domain.entity.Location

@Composable
fun CalfMapWebView(
    modifier: Modifier = Modifier,
    url: String,
    currentLocation: Location,
    destination: Location?,
) {
    val state = rememberWebViewState(url = url).apply {
        settings.javaScriptEnabled = true
    }

    LaunchedEffect(state.isLoading) {
        // Get the current loading state
    }

    AnimatedVisibility(destination == null) {
        state.evaluateJavascript(
            "createInfiniteLoopFunction(${currentLocation.lat},${currentLocation.lng})()",
            null
        )
    }
    LaunchedEffect(key1 = currentLocation) {
        destination?.let { location ->
            state.evaluateJavascript(
                "getDirections(${currentLocation.lat},${currentLocation.lng},${location.lat},${location.lng})",
                null
            )
        }
    }
    WebView(
        state = state,
        modifier = modifier.fillMaxSize()
    )
}