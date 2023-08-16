import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import java.awt.BorderLayout
import java.net.URL
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.border.EmptyBorder

@Composable
fun bPMapContent(htmlResource: URL) {
    SwingPanel(
        modifier = Modifier.fillMaxSize(),
        factory = { BPMap(htmlResource) }
    )
}

fun BPMap(
    htmlResource: URL
): JPanel {
    val jPanel = JPanel().apply {
        border = EmptyBorder(20, 20, 20, 20)
        layout = BorderLayout()

        // Create a JFXPanel for embedding JavaFX components
        val jfxPanel = JFXPanel()
        add(jfxPanel, BorderLayout.CENTER)

        Platform.runLater {
            val webView = WebView()
            val webEngine: WebEngine = webView.engine
            val scene = Scene(webView)
            jfxPanel.scene = scene
            val htmlContent = htmlResource.readText().trimIndent()
            webEngine.loadContent(htmlContent)
        }
    }
    SwingUtilities.invokeLater {
        jPanel.revalidate()
        jPanel.repaint()
    }
    return jPanel
}