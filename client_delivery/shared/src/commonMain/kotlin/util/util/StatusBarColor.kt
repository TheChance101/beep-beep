package util.util

import androidx.compose.runtime.Composable

expect class PlatformContext

@Composable
expect fun getPlatformContext(): PlatformContext

@Composable
expect fun setInsetsController(isDark: Boolean)
