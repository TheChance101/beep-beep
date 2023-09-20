package presentation.map

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import domain.entity.Location

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun MapWebView(
    modifier: Modifier,
    url: String,
    currentLocation: Location,
    destination: Location?,
) {
    var webView: WebView? by remember { mutableStateOf(null) }
    AnimatedVisibility(destination == null) {
        webView?.evaluateJavascript(
            "createInfiniteLoopFunction(${currentLocation.lat},${currentLocation.lng})()",
            null
        )
        webView?.evaluateJavascript("GetMap(${currentLocation.lat},${currentLocation.lng})", null)
    }
    destination?.let { location ->
        webView?.evaluateJavascript("clearMap()", null)

        webView?.evaluateJavascript(
            "getDirections(${location.lat},${location.lng})",
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
        it.loadUrl(url)
    }
}