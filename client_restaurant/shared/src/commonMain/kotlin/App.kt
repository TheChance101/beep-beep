import androidx.compose.runtime.Composable
import com.beepbeep.designSystem.DesignApp
import com.beepbeep.designSystem.ui.theme.BpTheme
import presentation.TestScreen

@Composable
fun App() {
    BpTheme {
        TestScreen()
    }
}