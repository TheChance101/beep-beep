package org.thechance.common.presentation.composables

import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import org.thechance.common.presentation.util.ContentType
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.SwingUtilities

private fun createWebViewComponent(
    content: String,
    contentType: ContentType,
    onError: (String) -> Unit,
    onAlert: (String) -> Unit = {},
    onLocationChange: (String) -> Unit = {},
): JFXPanel {
    val jfxPanel = JFXPanel()
    Platform.runLater {
        val webView = WebView()
        val webEngine = webView.engine
        val scene = Scene(webView)
        jfxPanel.scene = scene
        when (contentType) {
            ContentType.LINK -> webEngine.load(content)
            ContentType.SCRIPT -> webEngine.executeScript(content)
            ContentType.CONTENT -> webEngine.loadContent(getResourceContent(content))
        }
        webEngine.javaScriptEnabledProperty().set(true)

        webEngine.loadWorker.stateProperty().addListener { _, _, newState ->
            if (newState == Worker.State.SUCCEEDED) {
                val lat = 40.712776
                val lng = -74.005974
                webEngine.executeScript("updateLocation($lat, $lng)")
            }
        }

        webEngine.setOnError { error ->
            onError(error.message)
        }
        webEngine.setOnAlert { alert ->
            onAlert(alert.data.toString())
        }
        webEngine.locationProperty().addListener { _, _, newLocation ->
            onLocationChange(newLocation)
        }
    }
    return jfxPanel
}


private const val MAP = "#map="

fun webViewFromLink(): JPanel {
    val jPanel = JPanel().apply {
        layout = BorderLayout()
        val webViewPanel = createWebViewComponent(
            content = "https://www.openstreetmap.org/#map=15/33.2901/44.4034",
            contentType = ContentType.LINK,
            onError = { println(it) },
            onLocationChange = {
                println(
                    "Location: ${it.split(MAP)[1].substringBefore(MAP)}"
                )
            }
        )
        add(webViewPanel, BorderLayout.CENTER)
    }
    SwingUtilities.invokeLater {
        jPanel.revalidate()
        jPanel.repaint()
    }
    return jPanel
}


fun webViewFromContent(
    onGetAddress: (String) -> Unit,
): JPanel {
    val jPanel = JPanel().apply {
        layout = BorderLayout()
        val webViewPanel = createWebViewComponent(
            content = "/google_map.html",
            contentType = ContentType.CONTENT,
            onError = { println(it) },
            onAlert = { onGetAddress(it) }
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

