package domain.gateway

import domain.entity.Offer

interface IFakeRemoteGateway {
    fun getNewOffers(): List<Offer>
}