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
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.fontResources
import com.beepbeep.designSystem.ui.theme.BpTheme
import com.beepbeep.designSystem.ui.theme.Dimens
import com.beepbeep.designSystem.ui.theme.Theme
import com.beepbeep.designSystem.ui.theme.Typography
import com.beepbeep.designSystem.ui.theme.body
import com.beepbeep.designSystem.ui.theme.caption
import com.beepbeep.designSystem.ui.theme.headline
import com.beepbeep.designSystem.ui.theme.headlineLarge
import com.beepbeep.designSystem.ui.theme.title
import com.beepbeep.designSystem.ui.theme.titleLarge
import com.beepbeep.designSystem.ui.theme.titleMedium
import org.thechance.common.presentation.login.LoginScreen
import org.thechance.common.presentation.main.MainContainer
import org.thechance.common.presentation.resources.ProvideResources
import org.thechance.common.presentation.util.kms


val LocalScreenSize = compositionLocalOf<Size> { error("provide") }
private val localTypography = compositionLocalOf { Typography() }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    val typography = Typography(
        headlineLarge = headlineLarge(),
        headline = TextStyle(
            fontSize = 20.sp * 0.75,
            fontFamily = FontFamily(fontResources("roboto_medium")),
            fontWeight = FontWeight.W600,
        ),
        titleLarge = titleLarge(),
        title = title(),
        titleMedium = titleMedium(),
        body = body(),
        caption = caption(),
    )
    var size by remember { mutableStateOf(Size.Zero) }

    CompositionLocalProvider(LocalScreenSize provides size,
        localTypography provides typography,) {
        Box(Modifier.onSizeChanged { size = it.toSize() }) {
            BpTheme {
                Navigator(LoginScreen()) {
                    SlideTransition(it)
                }
            }
        }
    }
}
