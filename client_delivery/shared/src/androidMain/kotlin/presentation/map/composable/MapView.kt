package presentation.map.composable

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import domain.entity.Location
import kotlinx.coroutines.launch

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun MapView(
    modifier: Modifier,
    currentLocation: Location,
    destination: Location?,
) {
    var webView: WebView? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()
    AnimatedVisibility(destination == null) {
        LaunchedEffect(true) {
            webView?.evaluateJavascript(" GetMap()", null)
            webView?.evaluateJavascript("clearDirections()", null)
        }
    }

    LaunchedEffect(key1 = currentLocation) {
        destination?.let { location ->
            webView?.evaluateJavascript(" clearMap()", null)
            scope.launch {
                webView?.evaluateJavascript(
                    "getDirections(${currentLocation.latitude},${currentLocation.longitude},${location.latitude},${location.longitude})",
                    null
                )
                webView?.evaluateJavascript("clearDirections()", null)
            }
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                webView = this
                settings.javaScriptEnabled = true
            }
        }
    ) {
        it.loadUrl(MAP_URL)
    }
}
private const val MAP_URL = "File:///android_asset/bing_map/map/index.html"
