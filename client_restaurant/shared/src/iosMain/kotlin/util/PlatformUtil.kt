package util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
actual fun getNavigationBarPadding(): PaddingValues {
    return PaddingValues(bottom = 10.dp)
}
