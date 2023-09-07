package data.remote.mapper

import data.remote.model.OfferDto
import domain.entity.Offer

fun OfferDto.toEntity(): Offer {
    return Offer(
        id = id,
        image = image
    )
}