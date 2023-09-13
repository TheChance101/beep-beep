package util

import androidx.compose.runtime.Composable
import com.seiko.imageloader.ImageLoader

expect class PlatformContext

@Composable
expect fun getPlatformContext(): PlatformContext
expect fun generateImageLoader(applicationContext: PlatformContext): ImageLoader
