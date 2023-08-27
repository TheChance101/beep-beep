package org.thechance.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.toSize
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.thechance.common.presentation.main.MainContainer
import org.thechance.common.presentation.resources.ProvideResources


val LocalScreenSize = compositionLocalOf<Size> { error("provide") }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    var size by remember { mutableStateOf(Size.Zero) }

    CompositionLocalProvider(LocalScreenSize provides size,) {
        Box(Modifier.onSizeChanged { size = it.toSize() }) {
            BpTheme {
                ProvideResources {
                    Navigator(MainContainer) {
                        SlideTransition(it)
                    }
                }
            }
        }
    }
}
