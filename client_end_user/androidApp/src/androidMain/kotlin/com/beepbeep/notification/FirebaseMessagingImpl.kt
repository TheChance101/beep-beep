package com.beepbeep.notification

import com.google.firebase.messaging.FirebaseMessaging
import data.gateway.service.IFireBaseMessageService
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseMessagingImpl() : IFireBaseMessageService {
    override suspend fun getDeviceToken(): String {
        return suspendCoroutine {
            FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
                it.resume(token)
            }.addOnFailureListener { exception ->
                it.resumeWithException(exception)
            }
        }
    }
}