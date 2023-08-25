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
//        webEngine.loadWorker.stateProperty().addListener { _, _, newState ->
//            if (newState == Worker.State.SUCCEEDED) {
//                try {
////                    val latlng = currentLocation.split(",")
////                    val lat = latlng[0]
////                    val lng = latlng[1]
//                    webEngine.executeScript("initMap()")
//                } catch (e: Exception) {
//                    println(e.message)
//                }
//            }
//        }

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
    return jPanel
}

private fun getResourceContent(resourcePath: String): String? {
    val contentResource = object {}.javaClass.getResource(resourcePath)
    return contentResource?.readText()?.trimIndent()
}

