package util.statusbar

import androidx.compose.runtime.Composable


//@Suppress("FunctionName")
//fun WindowInsetsUIViewController(content: @Composable () -> Unit): UIViewController =
//    WindowInsetsUIViewController().apply {
//        setContent(content)
//    }
//
//internal class WindowInsetsUIViewController :
//    UIViewController,
//    UIViewControllerWithOverridesProtocol
//{
//    @OverrideInit
//    constructor() : super(nibName = null, bundle = null)
//
//    @OverrideInit
//    constructor(coder: NSCoder) : super(coder)
//
//    private lateinit var content: @Composable () -> Unit
//
//    private var _preferredStatusBarStyle: UIStatusBarStyle = 0L
//    override fun preferredStatusBarStyle(): UIStatusBarStyle =
//        _preferredStatusBarStyle
//
//    private var _prefersStatusBarHidden: Boolean by mutableStateOf(false)
//    override fun prefersStatusBarHidden(): Boolean =
//        _prefersStatusBarHidden
//
//    private var _prefersHomeIndicatorAutoHidden: Boolean by mutableStateOf(false)
//    override fun prefersHomeIndicatorAutoHidden(): Boolean =
//        _prefersHomeIndicatorAutoHidden
//
//    private var _preferredScreenEdgesDeferringSystemGestures: UIRectEdge = UIRectEdgeValue.None
//    override fun preferredScreenEdgesDeferringSystemGestures(): UIRectEdge =
//        _preferredScreenEdgesDeferringSystemGestures
//
//    private val windowInsetsController = object : UIKitWindowInsetsController {
//        private var preferredScreenEdgesDeferringSystemGesturesWhenHidden: UIRectEdge =
//            UIRectEdgeValue.None
//
//        override val isStatusBarVisible: Boolean
//            get() = !_prefersStatusBarHidden
//
//        override val isNavigationBarVisible: Boolean
//            get() = !_prefersHomeIndicatorAutoHidden
//
//        override fun setStatusBarContentColor(dark: Boolean) {
//            _preferredStatusBarStyle = if (dark) 3L else 1L
//            setNeedsStatusBarAppearanceUpdate()
//        }
//
//        override fun setNavigationBarsContentColor(dark: Boolean) {
//            // no op
//        }
//
//        override fun setIsStatusBarsVisible(isVisible: Boolean) {
//            _prefersStatusBarHidden = !isVisible
//            setNeedsStatusBarAppearanceUpdate()
//            applyScreenEdgesDeferringSystemGestures()
//        }
//
//        override fun setIsNavigationBarsVisible(isVisible: Boolean) {
//            _prefersHomeIndicatorAutoHidden = !isVisible
//            setNeedsUpdateOfHomeIndicatorAutoHidden()
//            applyScreenEdgesDeferringSystemGestures()
//        }
//
//        override fun setSystemBarsBehavior(behavior: SystemBarsBehavior) {
//            preferredScreenEdgesDeferringSystemGesturesWhenHidden =
//                behavior.preferredScreenEdgesDeferringSystemGesturesWhenHidden
//            applyScreenEdgesDeferringSystemGestures()
//        }
//
//        private fun applyScreenEdgesDeferringSystemGestures() {
//            _preferredScreenEdgesDeferringSystemGestures =
//                if (_prefersHomeIndicatorAutoHidden || _prefersStatusBarHidden) {
//                    preferredScreenEdgesDeferringSystemGesturesWhenHidden
//                } else {
//                    UIRectEdgeValue.None
//                }
//            setNeedsUpdateOfScreenEdgesDeferringSystemGestures()
//        }
//    }
//
//    override fun loadView() {
//        super.loadView()
//
//        val rootView = UIView()
//        val composeViewController = ComposeUIViewController {
//            CompositionLocalProvider(
//                LocalWindowInsetsController provides windowInsetsController,
//                content = content
//            )
//        }
//        addChildViewController(composeViewController)
//        rootView.addSubview(composeViewController.view)
//        rootView.setAutoresizesSubviews(true)
//        composeViewController.view.setAutoresizingMask(
//            UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight
//        )
//        view = rootView
//        composeViewController.didMoveToParentViewController(this)
//    }
//
//    fun setContent(
//        content: @Composable () -> Unit,
//    ) {
//        this.content = content
//    }
//}


@Composable
actual fun rememberWindowInsetsController(): WindowInsetsController? {
    return value
}


private val value: WindowInsetsController? = null