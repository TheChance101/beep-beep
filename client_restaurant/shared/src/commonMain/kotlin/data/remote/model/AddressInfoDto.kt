package data.remote.model

import data.remote.mapper.toEntity
import domain.entity.AddressInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressInfoDto(
    @SerialName("location")
    val location: LocationDto,
    @SerialName("address")
    val address: String
)