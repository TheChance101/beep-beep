package org.thechance.common.presentation.taxi

import org.thechance.common.domain.entity.CarColor
import org.thechance.common.presentation.base.BaseInteractionListener

interface TaxiScreenInteractionListener : BaseInteractionListener {

    fun onCancelCreateTaxiClicked()

    fun onTaxiPlateNumberChange(number: String)

    fun onDriverUserNamChange(name: String)

    fun onCarModelChanged(model: String)

    fun onCarColorSelected(color: CarColor)

    fun onSeatSelected(seats: Int)

    fun onCreateTaxiClicked()

    fun onAddNewTaxiClicked()

    fun onTaxiNumberChange(number: Int)

    fun onSearchInputChange(searchQuery: String)

}