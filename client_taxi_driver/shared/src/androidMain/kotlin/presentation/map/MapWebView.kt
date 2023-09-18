package presentation.map

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun MapWebView(
    modifier: Modifier,
    url: String,
    lat: String,
    lng: String,
) {
    var webView: WebView? by remember { mutableStateOf(null) }
    val jsCode = "createInfiniteLoopFunction($lat,$lng)()"
    webView?.evaluateJavascript(jsCode, null)
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