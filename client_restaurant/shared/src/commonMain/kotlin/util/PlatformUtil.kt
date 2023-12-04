package util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import io.ktor.client.engine.HttpClientEngine

@Composable
expect fun getNavigationBarPadding(): PaddingValues

@Composable
expect fun setInsetsController(isDark: Boolean)
@Composable
expect fun getStatusBarPadding(): PaddingValues


expect fun getEngine(): HttpClientEngine