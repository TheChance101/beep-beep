package org.thechance.common.ui.resturant

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import org.thechance.common.Testttt
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.FocusEvent
import javax.swing.AbstractAction
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.border.EmptyBorder

fun createWebViewComponent(): JFXPanel {
    val jfxPanel = JFXPanel()
    Platform.runLater {
        val webView = WebView()
        val webEngine: WebEngine = webView.engine
        val scene = Scene(webView)
        jfxPanel.scene = scene


      //  webEngine.load("https://www.google.com/maps/@47.8071501,-122.2181212,14z?entry=ttu")
        webEngine.load("https://www.openstreetmap.org/#map=15/33.2901/44.4034")


        webEngine.locationProperty().addListener { a, b, newLocation ->
            println("Location: ${newLocation.split("#map=")[1].substringBefore("#map=")}")
           // println("Location: $newLocation")
//            println("a: $a")
//            println("b: $b")
        }
        jfxPanel.actionMap.put("requestFocus", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                println("requestFocus")
                jfxPanel.requestFocusInWindow(FocusEvent.Cause.MOUSE_EVENT)
            }
        })

        webEngine.javaScriptEnabledProperty().set(true)
        println("userAgent: ${webEngine.userAgent}")
    }
    return jfxPanel
}

fun SwingComponent5(): JPanel {
    val jPanel = JPanel().apply {
        border = EmptyBorder(20, 20, 20, 20)
        layout = BorderLayout()

        val webViewPanel = createWebViewComponent()
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

