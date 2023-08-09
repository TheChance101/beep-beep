package org.thechance.service_notification.data.gateway

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IPushNotificationGateway

@Single
class PushNotificationGateway(private val firebaseMessaging: FirebaseMessaging) : IPushNotificationGateway {
    override suspend fun sendNotificationToUserByTokens(userTokens: List<String>, title: String, body: String) {
        for (token in userTokens) {
            val message = Message.builder()
                .putData(TITLE, title)
                .putData(BODY, body)
                .setToken(token)
                .build()
            firebaseMessaging.send(message)
        }
    }

    companion object {
        private const val TITLE = "title"
        private const val BODY = "body"
    }

}