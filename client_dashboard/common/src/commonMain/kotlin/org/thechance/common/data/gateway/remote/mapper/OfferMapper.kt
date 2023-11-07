package org.thechance.common.data.gateway.remote.mapper

import org.thechance.common.data.gateway.remote.model.OfferDto
import org.thechance.common.domain.entity.Offer

fun OfferDto.toEntity(): Offer{
    return Offer(
        id = id,
        name = name,
        image = image
    )
}

fun Offer.toDto(): OfferDto{
    return OfferDto(
        id = id,
        name = name,
        image = image
    )
}

fun List<OfferDto>.toEntity(): List<Offer> = map(OfferDto::toEntity)
