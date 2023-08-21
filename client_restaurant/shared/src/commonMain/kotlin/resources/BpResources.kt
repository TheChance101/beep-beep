package resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

data class BpResources(
    val ic_beep_beep_icon: String,
    val ic_beep_beep_logo: String,
    val arrow_left: String,
    val moon_stars: String,
    val sort: String,
    val sun: String,
)

private val BpLightResources = BpResources(
    ic_beep_beep_icon = "ic_beep_beep_icon.xml",
    ic_beep_beep_logo = "ic_beep_beep_logo.xml",
    arrow_left = "arrow_left.xml",
    moon_stars = "moon_stars.xml",
    sort = "sort.xml",
    sun = "sun.xml"
)

private val BpDarkResources = BpResources(
    ic_beep_beep_icon = "ic_beep_beep_icon.xml",
    ic_beep_beep_logo = "ic_beep_beep_logo.xml",
    arrow_left = "arrow_left.xml",
    moon_stars = "moon_stars.xml",
    sort = "sort.xml",
    sun = "sun.xml"
)

private val localResources = staticCompositionLocalOf { BpLightResources }

@Composable
fun BpResources(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val resources = if (useDarkTheme) BpDarkResources else BpLightResources

    CompositionLocalProvider(
        localResources provides resources
    ) {
        content()
    }
}


object Resources {
    val resources: BpResources
        @Composable
        @ReadOnlyComposable
        get() = localResources.current
}