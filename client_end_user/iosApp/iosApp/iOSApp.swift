import SwiftUI
import shared
import Firebase

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    init(){
        HelperKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ZStack {
                ContentView().edgesIgnoringSafeArea([.top])
            }
        }
    }
}



class AppDelegate: NSObject, UIApplicationDelegate {
 
    func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
    FirebaseApp.configure()
        registerForPushNotifications()

    return true
  }
    
    // MARK: - Notification Functions
     
     func registerForPushNotifications() {
       UNUserNotificationCenter.current()
         .requestAuthorization(options: [.alert, .sound, .badge]) {(granted, error) in
           print("Permission granted: \(granted)")
         }
     }
}
