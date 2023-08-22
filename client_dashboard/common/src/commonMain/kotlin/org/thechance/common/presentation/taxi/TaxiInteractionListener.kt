package org.thechance.common.presentation.taxi

import org.thechance.common.presentation.base.BaseInteractionListener

interface TaxiInteractionListener : BaseInteractionListener {
    fun onTaxiNumberChange(number: String)
    fun onSearchInputChange(searchQuery: String)
}