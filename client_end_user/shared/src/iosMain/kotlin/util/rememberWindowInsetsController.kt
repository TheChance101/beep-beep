package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIView
import platform.UIKit.UIViewAutoresizingFlexibleHeight
import platform.UIKit.UIViewAutoresizingFlexibleWidth
import platform.UIKit.UIViewController
import platform.UIKit.addChildViewController
import platform.UIKit.didMoveToParentViewController

@Suppress("FunctionName")
fun WindowInsetsUIViewController(content: @Composable () -> Unit): UIViewController =
    WindowInsetsUIViewController().apply {
        setContent(content)
    }

internal class WindowInsetsUIViewController : UIViewController {
    @OverrideInit
    constructor() : super(nibName = null, bundle = null)


    private lateinit var content: @Composable () -> Unit


    private val windowInsetsController = object : UIKitWindowInsetsController {


        override fun setStatusBarContentColor(color: Color, darkIcons: Boolean) {}


        override fun setNavigationBarsContentColor(dark: Boolean) {
            setNeedsStatusBarAppearanceUpdate()
        }

    }


    override fun loadView() {
        super.loadView()

        val rootView = UIView()
        val composeViewController = ComposeUIViewController {
            CompositionLocalProvider(
                LocalWindowInsetsController provides windowInsetsController,
                content = content
            )
        }

        addChildViewController(composeViewController)
        rootView.addSubview(composeViewController.view)
        rootView.setAutoresizesSubviews(true)
        composeViewController.view.setAutoresizingMask(UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight)
        view = rootView
        composeViewController.didMoveToParentViewController(this)
    }

    fun setContent(content: @Composable () -> Unit) {
        this.content = content
    }
}

@Stable
internal interface UIKitWindowInsetsController : WindowInsetsController {}

internal val LocalWindowInsetsController = staticCompositionLocalOf<UIKitWindowInsetsController?> {
    null
}


@Composable
actual fun rememberWindowInsetsController(): WindowInsetsController? {
    return LocalWindowInsetsController.current
}


