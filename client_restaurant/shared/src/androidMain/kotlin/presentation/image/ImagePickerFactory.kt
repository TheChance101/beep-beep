package presentation.image

import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

actual class ImagePickerFactory {

    @Composable
    actual fun createPicker(): ImagePicker {
        val activity = LocalContext.current as ComponentActivity
        return remember(activity) {
            ImagePicker(activity)
        }
    }
}

@Composable
actual fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap? {
    return remember(bytes) {
        if(bytes != null) {
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
        } else {
            null
        }
    }
}
