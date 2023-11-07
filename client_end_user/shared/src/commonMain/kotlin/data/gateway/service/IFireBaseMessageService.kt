package data.gateway.service

interface IFireBaseMessageService {
   suspend fun getDeviceToken(): String
}
