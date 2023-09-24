package presentation.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.mohamedrejeb.calf.ui.web.WebView
import com.mohamedrejeb.calf.ui.web.rememberWebViewState
import domain.entity.Location
import kotlinx.coroutines.launch

@Composable
fun CalfMapWebView(
    modifier: Modifier,
    url: String,
    currentLocation: Location,
    destination: Location?,
) {
    val state = rememberWebViewState(
        url = "File:///android_asset/bing_map/map/index.html"
    )
    state.settings.javaScriptEnabled = true
    val scope = rememberCoroutineScope()
    AnimatedVisibility(destination == null) {
        LaunchedEffect(true) {
            state.evaluateJavascript(" GetMap()", null)
            state.evaluateJavascript("clearDirections()", null)
        }
        state.evaluateJavascript(
            "createInfiniteLoopFunction(${currentLocation.lat},${currentLocation.lng})()",
            null
        )
    }
    LaunchedEffect(key1 = currentLocation) {
        destination?.let { location ->
            state.evaluateJavascript(" clearMap()", null)
            scope.launch {
                state.evaluateJavascript("clearDirections()", null)
                state.evaluateJavascript(
                    "getDirections(${currentLocation.lat},${currentLocation.lng},${location.lat},${location.lng})",
                    null
                )
            }
        }
    }
    WebView(
        state = state,
        modifier = Modifier
            .fillMaxSize()
    )
}