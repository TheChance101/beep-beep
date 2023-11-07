package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Offer
import org.thechance.common.domain.getway.IRestaurantGateway

interface IMangeOffersUseCase {

    suspend fun createOffer(offerName: String,image:ByteArray): Offer

}

class MangeOffersUseCase(
    private val restaurantGateway: IRestaurantGateway
) : IMangeOffersUseCase {
    override suspend fun createOffer(offerName: String, image: ByteArray): Offer {
        return restaurantGateway.createOffer(offerName,image)
    }
}