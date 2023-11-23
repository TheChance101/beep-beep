package data.service

interface IFirebaseMessagingService {
    suspend fun getDeviceToken(): String
}