package org.thechance.common.presentation.taxi

import org.thechance.common.domain.entity.CarColor

interface TaxiScreenInteractionListener {

    fun onCancelCreateTaxiClicked()

    fun onTaxiPlateNumberChange(number: String)

    fun onDriverUserNamChange(name: String)

    fun onCarModelChange(model: String)

    fun onCarColorSelected(color: CarColor)

    fun onSeatsSelected(seats: Int)

    fun onCreateTaxiClicked()

    fun onAddNewTaxiClicked()

}