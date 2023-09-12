package resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.beepbeep.designSystem.ui.theme.BpTheme
import resources.strings.ar.Arabic
import resources.strings.en.English
import resources.strings.IStringResources
import util.userCountryCode
import util.userLanguage
import util.setInsetsController

private val localDrawableResources = staticCompositionLocalOf { DrawableResources() }
private val localStringResources = staticCompositionLocalOf<IStringResources> { error("Darkness") }


@Composable
fun BeepBeepTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val drawableResources = if (useDarkTheme) BpDrawableDarkResources else DrawableResources()

    val language = userLanguage
    val countryCode = userCountryCode

    val stringResources = when (language) {
        "ar" -> Arabic()
        else -> English()
    }

    CompositionLocalProvider(
        localDrawableResources provides drawableResources,
        localStringResources provides stringResources,
    ) {
        BpTheme(useDarkTheme = useDarkTheme) {
            setInsetsController(useDarkTheme)
            content()
        }
    }
}

object Resources {
    val images: DrawableResources
        @Composable
        @ReadOnlyComposable
        get() = localDrawableResources.current

    val strings: IStringResources
        @Composable
        @ReadOnlyComposable
        get() = localStringResources.current
}
