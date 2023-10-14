package util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable



expect class PlatformContext

@Composable
expect fun getPlatformContext(): PlatformContext

@Composable
expect fun SetInsetsController(isDark: Boolean)

@Composable
expect fun getNavigationBarPadding(): PaddingValues

@Composable
expect fun getStatusBarPadding(): PaddingValues