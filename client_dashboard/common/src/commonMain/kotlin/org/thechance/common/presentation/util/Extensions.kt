package org.thechance.common.presentation.util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import java.io.ByteArrayInputStream

fun ByteArray.toImageBitmap(): ImageBitmap? {
    val inputStream = ByteArrayInputStream(this)
    return try {
        loadImageBitmap(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        inputStream.close()
    }
}
