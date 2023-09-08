package com.beepbeep

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            accompanistWebClient(url = "File:///android_asset/covid-19/map/index.html")
        }
    }

    @Composable
    fun accompanistWebClient(
        modifier: Modifier = Modifier,
        url: String,
    ) {

        val webViewState = rememberWebViewState(url = url)
        WebView(
            modifier = modifier,
            state = webViewState,
            captureBackPresses = true,
            onCreated = { it: WebView ->
                it.settings.javaScriptEnabled = true
                it.settings.allowFileAccess = true
            }
        )
    }
}