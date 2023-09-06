package com.beepbeep

import MainView
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.google.accompanist.web.rememberWebViewStateWithHTMLData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AccompanishWebClient(url="http://192.168.0.199:5500/map/index.html")
        }
    }
    @Composable
    fun AccompanishWebClient(
        modifier: Modifier = Modifier,
        url: String,
    ) {

        val webViewState = rememberWebViewState(url = url)
        WebView(
            modifier = modifier,
            state = webViewState,
            captureBackPresses = true,
            onCreated = { it : WebView ->
                it.settings.javaScriptEnabled = true
            }
        )
    }
}