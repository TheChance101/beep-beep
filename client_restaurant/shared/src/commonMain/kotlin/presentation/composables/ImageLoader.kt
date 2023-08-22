package presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.component.setupCommonComponents
import com.seiko.imageloader.rememberAsyncImagePainter

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    url: String,
) {
    CompositionLocalProvider(LocalImageLoader provides generateImageLoader()) {
        val painter = rememberAsyncImagePainter(url)
        Image(painter, null, modifier = modifier)
    }
}

private fun generateImageLoader(
    memCacheSize: Int = 32 * 1024 * 1024, //32MB
    diskCacheSize: Int = 512 * 1024 * 1024 //512MB
) = ImageLoader {
    interceptor {
        memoryCacheConfig {
            maxSizeBytes(memCacheSize)
        }
        diskCacheConfig {
            maxSizeBytes(diskCacheSize.toLong())
        }
    }
    components {
        setupCommonComponents()
    }
}