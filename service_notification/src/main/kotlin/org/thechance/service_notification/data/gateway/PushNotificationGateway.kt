package org.thechance.service_notification.data.gateway

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IPushNotificationGateway

@Single
class PushNotificationGateway(private val firebaseMessaging: FirebaseMessaging) : IPushNotificationGateway {
    override suspend fun sendNotification(userTokens: List<String>, title: String, body: String): Boolean {
        return firebaseMessaging.sendAll(userTokens.map {
            Message.builder()
                .putData(TITLE, title)
                .putData(BODY, body)
                .setToken(it)
                .build()
        }).failureCount == 0
    }

    companion object {
        private const val TITLE = "title"
        private const val BODY = "body"
    }

}