package org.thechance.service_notification.plugins

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.gson.JsonParser
import io.ktor.server.application.*
import java.io.ByteArrayInputStream

fun Application.configureFirebaseApp() {
    val credentialsString = System.getenv("firebase_key")
    val credentialsJson = JsonParser.parseString(credentialsString).asJsonObject
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(ByteArrayInputStream(credentialsJson.toString().toByteArray())))
        .build()
    FirebaseApp.initializeApp(options)
}