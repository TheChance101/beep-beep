package util

import androidx.core.view.WindowInsetsControllerCompat

actual class SystemBarsBehavior private constructor(
    internal val value: Int,
) {
    actual companion object {

        actual val Default: SystemBarsBehavior = SystemBarsBehavior(
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH
        )

        actual val Immersive: SystemBarsBehavior = SystemBarsBehavior(
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        )

    }
}
