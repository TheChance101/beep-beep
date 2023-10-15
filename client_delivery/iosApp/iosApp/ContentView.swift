import UIKit
import SwiftUI
import shared

//struct ComposeView: UIViewControllerRepresentable {
//    func makeUIViewController(context: Context) -> UIViewController {
//        Main_iosKt.MainViewController()
//    }
//
//- (UIInterfaceOrientationMask)supportedInterfaceOrientations {
//    // Return a bitmask of supported orientations. If you need more,
//    // use bitwise or (see the commented return).
//    return UIInterfaceOrientationMaskPortrait;
//    // return UIInterfaceOrientationMaskPortrait | UIInterfaceOrientationMaskPortraitUpsideDown;
//}
//    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
//}


struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.all, edges: .bottom) // Compose has own keyboard handler
    }
}



