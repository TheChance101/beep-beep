package data.remote.mapper

import data.remote.model.OfferDto
import domain.entity.Offer

fun OfferDto.toEntity() = Offer(
    id = id ?: "",
    title = name ?: "",
    restaurants = restaurants?.toEntity() ?: emptyList()
)


fun List<OfferDto>.toEntity() = map { it.toEntity() }
