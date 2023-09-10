package org.thechance.service_identity.di


import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.litote.kmongo.reactivestreams.KMongo

@Module
@ComponentScan("org.thechance.service_identity")
class IdentityModule

val kmongoModule = module {
    single {
        val cluster = System.getenv("cluster")
        val username = System.getenv("username")
        val password = System.getenv("password")

        val local = System.getenv("local")
        val connectionString = if (local.isNotEmpty()) {
            ConnectionString("mongodb://localhost:27017")
        } else {
            ConnectionString("mongodb+srv://$username:$password@$cluster.mongodb.net/")
        }
        val settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        KMongo.createClient(settings)
    }
}