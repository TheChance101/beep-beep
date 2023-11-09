import data.gateway.service.IFireBaseMessageService
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FireBaseMessageService : IFireBaseMessageService {
    override suspend fun getDeviceToken(): String {
//        return suspendCancellableCoroutine { continuation ->
//            FIRMessaging.messaging().tokenWithCompletion { token, error ->
//                if (token != null) {
//                    continuation.resume(token)
//                } else if (error != null) {
//                    continuation.resumeWithException(error)
//                } else {
//                    // Handle other cases if necessary
//                }
//            }
//        }
        return ""
    }
}
