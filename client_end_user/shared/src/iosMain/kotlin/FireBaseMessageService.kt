import cocoapods.FirebaseMessaging.FIRMessaging
import data.gateway.service.IFireBaseMessageService

class FireBaseMessageService : IFireBaseMessageService {

    override suspend fun getDeviceToken(): String {
        return FIRMessaging.messaging().FCMToken.toString()
    }
}

