package org.thechance.service_notification.data

import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.thechance.service_notification.data.collection.NotificationHistoryCollection
import org.thechance.service_notification.data.collection.TopicCollection
import org.thechance.service_notification.data.collection.UserCollection

@Single
class DatabaseContainer(val database: CoroutineDatabase) {

    val userCollection: CoroutineCollection<UserCollection> =
        database.getCollection<UserCollection>(USERS)

    val topicCollection: CoroutineCollection<TopicCollection> =
        database.getCollection<TopicCollection>(TOPICS)

    val historyCollection: CoroutineCollection<NotificationHistoryCollection> =
        database.getCollection<NotificationHistoryCollection>(HISTORY)

    companion object {
        const val USERS = "users"
        const val HISTORY = "history"
        const val TOPICS = "topics"
    }
}