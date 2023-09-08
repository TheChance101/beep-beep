package com.beepbeep

import MainView
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.google.accompanist.web.rememberWebViewStateWithHTMLData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AccompanishWebClient(url = "File:///android_asset/covid-19/map/index.html")
        }
    }
    @Composable
    fun AccompanishWebClient(
        modifier: Modifier = Modifier,
        url: String,
    ) {

        val webViewState = rememberWebViewState(url = url)
        var webView: WebView? by remember { mutableStateOf(null) }
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webView = this
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            // After the HTML file has loaded, send a value to it
                            val latitude = 40
                            val longitude = 70
                            view?.evaluateJavascript("GetMap('$latitude','$longitude')", null)
                        }
                    }
                }
            }
        ){
            it.loadUrl(url)
        }
    }
}