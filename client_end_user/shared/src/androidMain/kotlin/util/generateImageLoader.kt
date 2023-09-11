package util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import okio.Path.Companion.toOkioPath

actual class PlatformContext(val androidContext: Context)

@Composable
actual fun getPlatformContext(): PlatformContext = util.PlatformContext(LocalContext.current)
actual fun generateImageLoader(applicationContext: PlatformContext): ImageLoader {
    return ImageLoader {
        components {
            setupDefaultComponents(applicationContext.androidContext)
        }
        interceptor {
            memoryCacheConfig {
                // Set the max size to 25% of the app's available memory.
                maxSizePercent(applicationContext.androidContext, 0.25)
            }
            diskCacheConfig {
                directory(
                    applicationContext.androidContext.cacheDir.resolve("image_cache").toOkioPath()
                )
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}
