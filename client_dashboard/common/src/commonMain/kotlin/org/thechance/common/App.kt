package org.thechance.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.toSize
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.thechance.common.presentation.login.LoginScreen
import org.thechance.common.presentation.resources.ProvideResources


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    var size by remember { mutableStateOf(Size.Zero) }

    Box(Modifier.onSizeChanged { size = it.toSize() }) {
        BpTheme {
            ProvideResources {
                Navigator(org.thechance.common.presentation.main.MainContainer) {
                    SlideTransition(it)
                }
            }
        }
    }
}
