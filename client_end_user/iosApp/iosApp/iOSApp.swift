import SwiftUI
import shared
//import Firebase
//import FirebaseMessaging

@main
struct iOSApp: App {

//    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

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


/*class AppDelegate: NSObject, UIApplicationDelegate {

    func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {

        application.registerForRemoteNotifications()
        FirebaseApp.configure()

        let token = Messaging.messaging().fcmToken
        print("FCM token: \(token ?? "No token available")")

        registerForPushNotifications()

        return true
    }

    // MARK: - Notification Functions

    func registerForPushNotifications() {
        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .sound, .badge]) {(granted, error) in
            print("Permission granted: \(granted)")
        }
    }

    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
        print("APNs token: \(deviceToken)")

    }
}*/

