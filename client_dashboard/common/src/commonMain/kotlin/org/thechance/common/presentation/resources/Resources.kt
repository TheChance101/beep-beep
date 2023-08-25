package org.thechance.common.presentation.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val localResources = staticCompositionLocalOf { Strings }

object Resources {

    val Strings: StringResources
        @Composable
        get() = localResources.current

}

@Composable
fun ProvideResources(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(localResources provides Resources.Strings) {
        content()
    }
}

