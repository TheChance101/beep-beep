package org.thechance.service_notification.data

import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.coroutine
import org.thechance.service_notification.data.collection.NotificationCollection
import org.thechance.service_notification.data.collection.UserCollection
import org.thechance.service_notification.data.collection.UserNotificationsHistoryCollection


@Single
class DatabaseContainer(client: MongoClient) {
    private val database = client.coroutine.getDatabase(DATA_BASE_NAME)

    val userCollection = database.getCollection<UserCollection>(USER_COLLECTION)
    val notificationsCollection = database.getCollection<NotificationCollection>(USER_NOTIFICATION_HISTORY_COLLECTION)
    val historyCollection = database.getCollection<UserNotificationsHistoryCollection>(NOTIFICATIONS_COLLECTION)

    companion object {
        private const val DATA_BASE_NAME = "TheChanceBeepBeep"
        private const val USER_COLLECTION = "users"
        private const val USER_NOTIFICATION_HISTORY_COLLECTION = "user_notification_history"
        private const val NOTIFICATIONS_COLLECTION = "notifications"
    }

}