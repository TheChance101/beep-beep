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
import com.beepbeep.designSystem.ui.theme.Dimens
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.login.LoginScreen
import org.thechance.common.presentation.util.kms

val LocalScreenSize = compositionLocalOf<Size> { error("provide") }
val LocalDimensions = compositionLocalOf<Dimens> { error("provide") }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    var size by remember { mutableStateOf(Size.Zero) }

    CompositionLocalProvider(
        LocalScreenSize provides size,
        LocalDimensions provides Theme.dimens.toKms()
    ) {
        Box(Modifier.onSizeChanged { size = it.toSize() }) {
            BpTheme {
                Navigator(LoginScreen()) {
                    SlideTransition(it)
                }
            }
        }
    }
}

@Composable
fun Dimens.toKms(): Dimens {
    return copy(
        space1 = 1.kms,
        space4 = 4.kms,
        space8 = 8.kms,
        space16 = 16.kms,
        space24 = 24.kms,
        space32 = 32.kms,
        space40 = 40.kms,
        space100 = 100.kms,
    )
}
