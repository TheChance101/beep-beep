package org.thechance.common.data.service

import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.data.remote.model.TaxiDto
import java.io.File


interface IFakeService {
    fun getTaxiPDFReport(): File
}