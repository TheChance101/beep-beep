package presentation.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import androidx.compose.ui.interop.LocalUIViewController
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import platform.UIKit.UIViewController

actual class ImagePickerFactory actual constructor(private val context: PlatformContext) {

    @Composable
    actual fun createPicker(): ImagePicker {
        val rootController = context.iosController.current
        return remember {
            ImagePicker(rootController)
        }
    }
}

@Composable
actual fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap? {
    return remember(bytes) {
        if (bytes != null) {
            Bitmap.makeFromImage(
                Image.makeFromEncoded(bytes)
            ).asComposeImageBitmap()
        } else {
            null
        }
    }
}
