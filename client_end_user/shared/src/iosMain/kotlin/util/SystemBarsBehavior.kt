package util

import platform.UIKit.UIRectEdge

actual class SystemBarsBehavior private constructor(
    internal val preferredScreenEdgesDeferringSystemGesturesWhenHidden: UIRectEdge,
) {
    actual companion object {

        actual val Default: SystemBarsBehavior = SystemBarsBehavior(
            UIRectEdgeValue.None
        )


        actual val Immersive: SystemBarsBehavior = SystemBarsBehavior(
            UIRectEdgeValue.All
        )
    }
}
