package org.thechance.common.ui.resturant

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import org.thechance.common.Testttt
import java.awt.BorderLayout
import java.awt.event.FocusEvent
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.border.EmptyBorder

private fun createWebViewComponent(
    resource: String,
    resourceType: ResourceType,
    onLocationChange: (String) -> Unit = {},
    onError: (String) -> Unit,
    onAlert: (String) -> Unit = {},
): JFXPanel {
    val jfxPanel = JFXPanel()
    Platform.runLater {
        val webView = WebView()
        val webEngine = webView.engine
        val scene = Scene(webView)
        jfxPanel.scene = scene
        when (resourceType) {
            ResourceType.LINK -> webEngine.load(resource)
            ResourceType.SCRIPT -> webEngine.executeScript(resource)
            ResourceType.CONTENT -> webEngine.loadContent(getResourceContent(resource))
        }
        webEngine.javaScriptEnabledProperty().set(true)
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

fun webViewFromLink(link:String): JPanel {
    val jPanel = JPanel().apply {
        border = EmptyBorder(20, 20, 20, 20)
        layout = BorderLayout()
        val webViewPanel = createWebViewComponent(
            resource = "https://www.google.com/maps/@30.063854,31.3789053,15z?entry=ttu",
            resourceType = ResourceType.LINK,
            onError = { println(it) }
        )
        add(webViewPanel, BorderLayout.CENTER)
    }
    SwingUtilities.invokeLater {
        jPanel.revalidate()
        jPanel.repaint()
    }
    return jPanel
}

fun webViewFromLinkOpenStreetMap(): JPanel {
    val jPanel = JPanel().apply {
        border = EmptyBorder(20, 20, 20, 20)
        layout = BorderLayout()
        val webViewPanel = createWebViewComponent(
            resource = "https://www.openstreetmap.org/#map=15/33.2901/44.4034",
            resourceType = ResourceType.LINK,
            onError = { println(it) },
            onLocationChange = { println("Location: ${it.split("#map=")[1].substringBefore("#map=")}") }
        )
        add(webViewPanel, BorderLayout.CENTER)
    }
    SwingUtilities.invokeLater {
        jPanel.revalidate()
        jPanel.repaint()
    }
    return jPanel
}

fun SwingComponent4(): JPanel {
    val jPanel = JPanel().apply {
        border = EmptyBorder(20, 20, 20, 20)
        layout = BorderLayout()

        val jfxPanel = JFXPanel()
        add(jfxPanel, BorderLayout.CENTER)

        jfxPanel.requestFocusInWindow(FocusEvent.Cause.MOUSE_EVENT)
        Platform.runLater {
            val webView = WebView()
            val webEngine: WebEngine = webView.engine
            val scene = Scene(webView)
            jfxPanel.scene = scene

            val htmlResource = Testttt::class.java.getResource("/testweb.html")
            val htmlContent = htmlResource?.readText()?.trimIndent()

            webEngine.loadContent(htmlContent)
            webEngine.javaScriptEnabledProperty().set(true)

        }

    }
    SwingUtilities.invokeLater {
        jPanel.revalidate()
        jPanel.repaint()
    }
    return jPanel
}


private fun getResourceContent(resourcePath: String): String {
    val inputStream = object {}.javaClass.getResourceAsStream(resourcePath)
    return inputStream?.bufferedReader().use { it?.readText().toString() }
}

