package presentation.map

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.mohamedrejeb.calf.ui.web.rememberWebViewState
import kotlinx.coroutines.delay

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun MapWebView(
    modifier: Modifier,
    url: String,
) {
    var webView: WebView? by remember { mutableStateOf(null) }
    var latitude2 by remember { mutableDoubleStateOf(40.6740229) }


    LaunchedEffect(Unit) {
        while (true) {
            // Update latitude and longitude values here
            latitude2 += .00002
            if (latitude2 >= 180)
                latitude2 = 0.0

            // Send updated values to the JavaScript function

            val jsCode = "createInfiniteLoopFunction($latitude2)()"

            webView?.evaluateJavascript(jsCode, null)
            // Delay for a specified interval before the next update (e.g., 5 seconds)

            delay(1000)

        }
    }
    val webViewState = rememberWebViewState(url = url)

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                webView = this
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        webView?.evaluateJavascript("GetMap()", null)
                    }
                }
            }
        }
    ) {
        it.loadUrl(url)
    }
}