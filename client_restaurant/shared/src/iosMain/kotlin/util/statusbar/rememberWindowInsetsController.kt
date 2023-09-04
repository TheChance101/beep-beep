package util.statusbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.window.ComposeUIViewController
import platform.Foundation.NSCoder
import platform.UIKit.UIRectEdge
import platform.UIKit.UIStatusBarStyle
import platform.UIKit.UIView
import platform.UIKit.UIViewAutoresizingFlexibleHeight
import platform.UIKit.UIViewAutoresizingFlexibleWidth
import platform.UIKit.UIViewController
import platform.UIKit.addChildViewController
import platform.UIKit.didMoveToParentViewController
import platform.UIKit.setNeedsUpdateOfHomeIndicatorAutoHidden
import platform.UIKit.setNeedsUpdateOfScreenEdgesDeferringSystemGestures


@Suppress("FunctionName")
fun WindowInsetsUIViewController(content: @Composable () -> Unit): UIViewController =
    WindowInsetsUIViewController().apply {
        setContent(content)
    }

internal class WindowInsetsUIViewController : UIViewController {
    @OverrideInit
    constructor() : super(nibName = null, bundle = null)

    @OverrideInit
    constructor(coder: NSCoder) : super(coder)


    private lateinit var content: @Composable () -> Unit

    private var _preferredStatusBarStyle: UIStatusBarStyle = 0L
    override fun preferredStatusBarStyle(): UIStatusBarStyle = _preferredStatusBarStyle

    private var _prefersStatusBarHidden: Boolean by mutableStateOf(false)
    override fun prefersStatusBarHidden(): Boolean = _prefersStatusBarHidden

    private var preferredScreenEdgesDeferringSystemGesturesWhenHidden: UIRectEdge =
        UIRectEdgeValue.None


    private var _preferredScreenEdgesDeferringSystemGestures: UIRectEdge = UIRectEdgeValue.None
    fun preferredScreenEdgesDeferringSystemGestures(): UIRectEdge =
        _preferredScreenEdgesDeferringSystemGestures

    private var _prefersHomeIndicatorAutoHidden: Boolean by mutableStateOf(false)
    fun prefersHomeIndicatorAutoHidden(): Boolean =
        _prefersHomeIndicatorAutoHidden

    private val windowInsetsController = object : UIKitWindowInsetsController {
        private var preferredScreenEdgesDeferringSystemGesturesWhenHidden: UIRectEdge =
            UIRectEdgeValue.None

        override val isStatusBarVisible: Boolean
            get() = !_prefersStatusBarHidden

        override val isNavigationBarVisible: Boolean
            get() = !_prefersHomeIndicatorAutoHidden

        override fun setStatusBarContentColor(dark: Boolean) {
            _preferredStatusBarStyle = if (dark) 3L else 1L
            setNeedsStatusBarAppearanceUpdate()
        }

        override fun setNavigationBarsContentColor(dark: Boolean) {
            _preferredStatusBarStyle = if (dark) 3L else 1L
            setNeedsStatusBarAppearanceUpdate()
        }

        override fun setIsStatusBarsVisible(isVisible: Boolean) {
            _prefersStatusBarHidden = !isVisible
            setNeedsStatusBarAppearanceUpdate()
            applyScreenEdgesDeferringSystemGestures()
        }

        override fun setIsNavigationBarsVisible(isVisible: Boolean) {
            _prefersHomeIndicatorAutoHidden = !isVisible
            setNeedsUpdateOfHomeIndicatorAutoHidden()
            applyScreenEdgesDeferringSystemGestures()
        }

        override fun setSystemBarsBehavior(behavior: SystemBarsBehavior) {
            preferredScreenEdgesDeferringSystemGesturesWhenHidden =
                behavior.preferredScreenEdgesDeferringSystemGesturesWhenHidden
            applyScreenEdgesDeferringSystemGestures()

        }
    }

    private fun applyScreenEdgesDeferringSystemGestures() {
        _preferredScreenEdgesDeferringSystemGestures =
            if (_prefersHomeIndicatorAutoHidden || _prefersStatusBarHidden) {
                    preferredScreenEdgesDeferringSystemGesturesWhenHidden
            } else {
                UIRectEdgeValue.None
            }
        setNeedsUpdateOfScreenEdgesDeferringSystemGestures()
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
internal interface UIKitWindowInsetsController : WindowInsetsController {
    val isStatusBarVisible: Boolean
    val isNavigationBarVisible: Boolean
}

internal val LocalWindowInsetsController = staticCompositionLocalOf<UIKitWindowInsetsController?> {
    null
}


@Composable
actual fun rememberWindowInsetsController(): WindowInsetsController? {
    return LocalWindowInsetsController.current
}


internal object UIRectEdgeValue {
    val None: UIRectEdge = 0.toULong()
    val Top: UIRectEdge = (1 shl 0).toULong()
    val Left: UIRectEdge = (1 shl 1).toULong()
    val Bottom: UIRectEdge = (1 shl 2).toULong()
    val Right: UIRectEdge = (1 shl 3).toULong()
    val All: UIRectEdge = Top or Left or Bottom or Right
}