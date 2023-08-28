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
import org.koin.java.KoinJavaComponent.inject
import org.thechance.common.presentation.app.ThemeScreenModel
import org.thechance.common.presentation.main.MainContainer
import org.thechance.common.presentation.resources.ProvideResources


val LocalScreenSize = compositionLocalOf<Size> { error("provide") }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    var size by remember { mutableStateOf(Size.Zero) }
    val themeScreenModel by inject<ThemeScreenModel>(ThemeScreenModel::class.java)
    val themeMode by themeScreenModel.state.collectAsState()
    CompositionLocalProvider(LocalScreenSize provides size) {
        Box(Modifier.onSizeChanged { size = it.toSize() }) {
            BpTheme(useDarkTheme = themeMode) {
                ProvideResources {
                    Navigator(MainContainer) {
                        SlideTransition(it)
                    }
                }
            }
        }
    }
}
