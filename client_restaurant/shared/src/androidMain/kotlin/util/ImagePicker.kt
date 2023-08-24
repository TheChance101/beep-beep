package util

import android.content.Context
import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext


actual class PlatformContext(val androidContext: Context)

@Composable
actual fun getPlatformContext(): PlatformContext = util.PlatformContext(LocalContext.current)

actual class ImagePicker(
    private val activity: ComponentActivity
) {
    private lateinit var getContent: ActivityResultLauncher<String>

    @Composable
    actual fun registerPicker(onImagePicked: (ByteArray) -> Unit) {
        getContent = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                activity.contentResolver.openInputStream(uri)?.use {
                    onImagePicked(it.readBytes())
                }
            }
        }
    }

    actual fun pickImage() {
        getContent.launch("image/*")
    }
}

actual class ImagePickerFactory actual constructor(context: PlatformContext){

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
