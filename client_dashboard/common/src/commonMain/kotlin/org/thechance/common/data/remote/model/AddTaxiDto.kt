package org.thechance.common.data.remote.model


data class AddTaxiDto(
    val isAddNewTaxiDialogVisible: Boolean,
    val taxiPlateNumber: String,
    val driverUserName: String,
    val carModel: String,
    val selectedCarColor: Int,
    val seats: Int
)
