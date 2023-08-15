package org.thechance.service_notification.plugins

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.*

fun Application.configureFirebaseApp(){
    val serviceAccount = this::class.java.classLoader.getResourceAsStream("ktor_firebase_auth.json")
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()
    FirebaseApp.initializeApp(options)
}