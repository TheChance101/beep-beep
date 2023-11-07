package util

import androidx.compose.runtime.Composable
import com.seiko.imageloader.ImageLoader
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module

expect class PlatformContext

@Composable
expect fun getPlatformContext(): PlatformContext
expect fun generateImageLoader(applicationContext: PlatformContext): ImageLoader
