package org.thechance.common.presentation.taxi

import org.thechance.common.domain.entity.CarColor
import org.thechance.common.presentation.base.BaseInteractionListener

interface TaxiScreenInteractionListener : BaseInteractionListener
, FilterMenuListener, TaxiMenuListener, PageListener,TaxiDialogListener{

    fun onExportReportClicked()
    fun onDismissExportReportSnackBar()


    fun onTaxiNumberChange(number: Int)

    fun onSearchInputChange(searchQuery: String)
    fun onAddNewTaxiClicked()


}
interface TaxiDialogListener {
    fun onCancelCreateTaxiClicked()

    fun onTaxiPlateNumberChange(number: String)

    fun onDriverUserNamChange(name: String)

    fun onCarModelChanged(model: String)

    fun onCarColorSelected(color: CarColor)

    fun onSeatSelected(seats: Int)

    fun onCreateTaxiClicked()

}
interface TaxiMenuListener {
    fun showTaxiMenu(username: String)
    fun hideTaxiMenu()
    fun onDeleteTaxiClicked(taxi: TaxiDetailsUiState)
    fun onEditTaxiClicked(taxi: TaxiDetailsUiState)
    fun onSaveEditTaxiMenu()
    fun onCancelEditTaxiMenu()
}
interface FilterMenuListener {
    fun showFilterMenu()
    fun hideFilterMenu()
}
interface PageListener {
    fun onItemsIndicatorChange(itemPerPage: Int)
    fun onPageClick(pageNumber: Int)
}