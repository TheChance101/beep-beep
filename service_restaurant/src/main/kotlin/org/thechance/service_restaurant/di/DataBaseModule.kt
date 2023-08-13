package org.thechance.service_restaurant.di

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import org.bson.UuidRepresentation
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

@Module
class DataBaseModule {

    val cluster = System.getenv("cluster")
    val username = System.getenv("username")
    val password = System.getenv("password")

    @Single
    fun provideKmongoSettings(connectionString: ConnectionString) = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .build()

    @Single
    fun provideConnectionString() =
        ConnectionString("mongodb+srv://$username:$password@$cluster.mongodb.net/")

    @Single
    fun provideKMongoClient(settings: MongoClientSettings) = KMongo.createClient(settings)

    @Single
    fun provideCoroutineDataBase(client: MongoClient): CoroutineDatabase = client.coroutine.getDatabase(DATABASE_NAME)

    private companion object {
        const val DATABASE_NAME = "TheChanceBeepBeep"
    }

}