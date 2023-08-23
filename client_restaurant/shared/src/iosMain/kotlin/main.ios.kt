import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import presentation.image.ImagePickerFactory

fun MainViewController() = ComposeUIViewController {
    App(imagePicker = ImagePickerFactory(LocalUIViewController.current).createPicker())
}


