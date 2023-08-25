package org.thechance.common.data.service

import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.data.remote.model.TaxiDto
import java.io.File


interface IFakeService {
    fun getTaxiPDFReport(): File

    fun getRestaurants(): List<RestaurantDto>

    fun getTaxis(): List<TaxiDto>

    fun findTaxisByUsername(username: String): List<TaxiDto>
    fun searchRestaurantsByRestaurantName(restaurantName: String): List<RestaurantDto>

    fun addTaxi(taxiDto: TaxiDto): TaxiDto
}