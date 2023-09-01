package org.thechance.common.presentation.composables

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.SwingUtilities

private fun createWebViewComponent(
    content: String,
    currentLocation: String,
    onError: (String) -> Unit,
    onAlert: (String) -> Unit = {},
): JFXPanel {
    val jfxPanel = JFXPanel()
    Platform.runLater {
        val webView = WebView()
        val webEngine = webView.engine
        val scene = Scene(webView)
        jfxPanel.scene = scene
        webEngine.loadContent(getResourceContent(content))
        webEngine.javaScriptEnabledProperty().set(true)
        webEngine.setOnError { error ->
            onError(error.message)
        }
        webEngine.setOnAlert { alert ->
            onAlert(alert.data.toString())
        }
    }
    return jfxPanel
}

private fun getResourceContent(resourcePath: String): String? {
    val contentResource = object {}.javaClass.getResource(resourcePath)
    return contentResource?.readText()?.trimIndent()
}


private const val GOOGLE_MAP_PATH = "/google_map.html"

fun mapFromWebView(
    currentLocation: String,
    onGetLocation: (String) -> Unit,
): JPanel {
    val jPanel = JPanel().apply {
        layout = BorderLayout()
        val webViewPanel = createWebViewComponent(
            content = GOOGLE_MAP_PATH,
            onError = { println(it) },
            onAlert = { onGetLocation(it) },
            currentLocation = currentLocation,
        )
        add(webViewPanel, BorderLayout.CENTER)
    }
    SwingUtilities.invokeLater {
        jPanel.revalidate()
        jPanel.repaint()
    }
    return jPanel
}


