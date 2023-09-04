package domain.usecase

import domain.entity.Offer
import domain.gateway.IFakeRemoteGateway


interface IGetNewOffersUserCase {
    suspend fun getNewOffers(limit : Int = DEFAULT_OFFER_LIMIT): List<Offer>

    private companion object {
        const val DEFAULT_OFFER_LIMIT = 3
    }
}

class GetNewOffersUserCase(
    private val fakeRemoteGateway: IFakeRemoteGateway
) : IGetNewOffersUserCase {
    override suspend fun getNewOffers(limit: Int): List<Offer> {
        return fakeRemoteGateway.getNewOffers().take(limit)
    }


}