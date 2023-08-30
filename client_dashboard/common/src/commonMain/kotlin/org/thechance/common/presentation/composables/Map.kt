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
        println("webview:" + webView.toString())
        println("webview:" + webView.isVisible.toString())
        println("webengine:" + webEngine.isJavaScriptEnabled.toString())
        println("webengine:" + webEngine.toString())
        println("scene height:" + scene.height.toString())
    }
    println("jfxpanel:" + jfxPanel.isVisible.toString())
    println("scene:" + jfxPanel.scene.toString())
    return jfxPanel
}


fun webViewFromContent(
    currentLocation: String,
    onGetAddress: (String) -> Unit,
): JPanel {
    val jPanel = JPanel().apply {
        layout = BorderLayout()
        val webViewPanel = createWebViewComponent(
            content = "/google_map.html",
            onError = { println(it) },
            onAlert = { onGetAddress(it) },
            currentLocation = currentLocation,
        )
        add(webViewPanel, BorderLayout.CENTER)
    }
    SwingUtilities.invokeLater {
        jPanel.revalidate()
        jPanel.repaint()
    }
    println("jpanel:" + jPanel.isVisible.toString())
    return jPanel
}

private fun getResourceContent(resourcePath: String): String? {
    val contentResource = object {}.javaClass.getResource(resourcePath)
    return contentResource?.readText()?.trimIndent()
}

