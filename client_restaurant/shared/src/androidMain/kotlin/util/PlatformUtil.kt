package util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import io.ktor.client.engine.HttpClientEngine

@Composable
actual fun getNavigationBarPadding(): PaddingValues {
    return WindowInsets.navigationBars.asPaddingValues()
}

