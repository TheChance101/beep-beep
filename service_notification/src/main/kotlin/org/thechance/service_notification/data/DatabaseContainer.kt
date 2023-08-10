package org.thechance.service_notification.data

import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.thechance.service_notification.data.collection.NotificationHistoryCollection
import org.thechance.service_notification.data.collection.UserCollection

@Single
class DatabaseContainer(val database: CoroutineDatabase) {

    val userCollection: CoroutineCollection<UserCollection> =
        database.getCollection<UserCollection>("users")

    val historyCollection: CoroutineCollection<NotificationHistoryCollection> =
        database.getCollection<NotificationHistoryCollection>("history_notification")
}