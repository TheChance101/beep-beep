package org.thechance.service_taxi.data

import org.litote.kmongo.coroutine.CoroutineClient
import org.thechance.service_taxi.data.collection.TaxiCollection
import org.thechance.service_taxi.data.collection.TripCollection

class DataBaseContainer(client: CoroutineClient) {
    private val database by lazy { client.getDatabase(DATA_BASE_NAME) }

    val tripCollection by lazy { database.getCollection<TripCollection>(TRIP_COLLECTION_NAME) }

    val taxiCollection by lazy { database.getCollection<TaxiCollection>(TAXI_COLLECTION_NAME) }

    companion object {
        private val DATA_BASE_NAME = System.getenv("DB_NAME").toString()
        private const val TAXI_COLLECTION_NAME = "taxi"
        private const val TRIP_COLLECTION_NAME = "trip"
    }
}