import platform.UIKit.UIViewController
import util.statusbar.WindowInsetsUIViewController

fun MainViewController(): UIViewController {
    return WindowInsetsUIViewController {
        App()
    }
}


