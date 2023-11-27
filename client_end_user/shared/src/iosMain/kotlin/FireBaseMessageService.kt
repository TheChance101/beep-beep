import cocoapods.FirebaseMessaging.FIRMessaging
import data.gateway.service.IFireBaseMessageService
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FireBaseMessageService : IFireBaseMessageService {

    override suspend fun getDeviceToken(): String = suspendCoroutine { cont ->
        FIRMessaging.messaging().tokenWithCompletion { token, error ->
            cont.resume(token ?: "null token")
        }
    }

}

