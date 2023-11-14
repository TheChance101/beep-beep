package util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

@Composable
actual fun setInsetsController(isDark: Boolean) {
}

@Composable
actual fun getNavigationBarPadding(): PaddingValues {
    return PaddingValues(bottom = 10.dp)
}

@Composable
actual fun getStatusBarPadding(): PaddingValues {
    return PaddingValues(top = 35.dp)
}


actual fun getEngine(): HttpClientEngine = Darwin.create()
