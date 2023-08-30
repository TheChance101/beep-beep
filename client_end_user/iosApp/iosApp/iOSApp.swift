import SwiftUI

@main
struct iOSApp: App {
    init(){
        HelperKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
		    ZStack {
		        Color.white.ignoresSafeArea(.all) // status bar color
			    ContentView()
			}.preferredColorScheme(.light)
		}
	}
}