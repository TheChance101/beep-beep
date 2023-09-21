package presentation.map.composable

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun MapView(
    modifier: Modifier,
    currentLocation: Pair<Double, Double>,
    destination: Pair<Double, Double>?,
) {

    var webView: WebView? by remember { mutableStateOf(null) }

    AnimatedVisibility(destination == null) {
        val jsCode = "createInfiniteLoopFunction(${currentLocation.first},${currentLocation.second})()"
        webView?.evaluateJavascript(jsCode, null)
        webView?.evaluateJavascript("GetMap(${currentLocation.first},${currentLocation.second})", null)
    }
    destination?.let { location ->
        webView?.evaluateJavascript("clearMap()", null)

        webView?.evaluateJavascript(
            "getDirections(${location.first},${location.second})",
            null
        )
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

private const val MAP_URL = "File:///android_asset/bing_map/map/index2.html"
