package presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.rememberAsyncImagePainter
import util.generateImageLoader
import util.getPlatformContext

@Composable
fun BpImageLoader(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val context = getPlatformContext()
    CompositionLocalProvider(LocalImageLoader provides remember { generateImageLoader(context) }) {
        Image(
            modifier = modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(imageUrl, contentScale = contentScale),
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    }
}