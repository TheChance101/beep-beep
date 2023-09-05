package org.thechance.common.presentation.taxi

import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.util.TaxiStatus
import org.thechance.common.presentation.base.BaseInteractionListener

interface TaxiInteractionListener : BaseInteractionListener, FilterMenuListener,
    TaxiMenuListener, PageListener, TaxiDialogListener {

    fun onExportReportClicked()

    fun onDismissExportReportSnackBar()

    fun onSearchInputChange(searchQuery: String)

    fun onAddNewTaxiClicked()
}

interface TaxiDialogListener {

    fun onTaxiPlateNumberChange(number: String)

    fun onDriverUserNamChange(name: String)

    fun onCarModelChanged(model: String)

    fun onCarColorSelected(color: CarColor)

    fun onSeatSelected(seats: Int)

    fun onSaveClicked()

    fun onCancelClicked()

    fun onCreateTaxiClicked()
}

interface TaxiMenuListener {

    fun showTaxiMenu(taxiId: String)

    fun hideTaxiMenu()

    fun onDeleteTaxiClicked(taxi: String)

    fun onEditTaxiClicked(taxi: TaxiDetailsUiState)
}

interface FilterMenuListener {
    fun onCancelFilterClicked()

    fun onSaveFilterClicked()

    fun onFilterMenuDismiss()

    fun onFilterMenuClicked()

    fun onSelectedCarColor(color: CarColor)

    fun onSelectedSeat(seats: Int)

    fun onSelectedStatus(status: TaxiStatus)

    fun onClearAllClicked()
}

interface PageListener {
    fun onItemsIndicatorChange(itemPerPage: Int)

    fun onPageClick(pageNumber: Int)
}