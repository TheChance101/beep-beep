import cocoapods.FirebaseMessaging.FIRMessaging
import data.gateway.service.IFireBaseMessageService


class FireBaseMessageService : IFireBaseMessageService {

    override suspend fun getDeviceToken(): String {
        val token = FIRMessaging.messaging().APNSToken.toString() ?: ""
        println("Tokein = ${token}")
        return "token"
    }
}
