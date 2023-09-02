package util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

expect class ImagePickerFactory(context: PlatformContext) {


    @Composable
    fun createPicker(): ImagePicker
}

expect class ImagePicker {
    @Composable
    fun registerPicker(onImagePicked: (ByteArray) -> Unit)

    fun pickImage()
}

@Composable
expect fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap?


expect class PlatformContext

@Composable
expect fun getPlatformContext(): PlatformContext
