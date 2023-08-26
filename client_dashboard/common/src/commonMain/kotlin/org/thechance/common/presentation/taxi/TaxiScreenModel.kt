package org.thechance.common.presentation.taxi

import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.usecase.ICreateNewTaxiUseCase
import org.thechance.common.domain.usecase.IFindTaxiByUsernameUseCase
import org.thechance.common.domain.usecase.IGetTaxiReportUseCase
import org.thechance.common.domain.usecase.IGetTaxisUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class TaxiScreenModel(
    private val getTaxis: IGetTaxisUseCase,
    private val createNewTaxi: ICreateNewTaxiUseCase,
    private val findTaxiByUsername: IFindTaxiByUsernameUseCase,
    private val getTaxiReport: IGetTaxiReportUseCase
) : BaseScreenModel<TaxiUiState, TaxiUiEffect>(TaxiUiState()), TaxiScreenInteractionListener {

    init {
        getDummyTaxiData()
    }

    //region taxi menu
    override fun onDeleteTaxiClicked(taxi: TaxiDetailsUiState) {
        println("delete taxi")
        //todo: delete taxi
    }

    override fun onEditTaxiClicked(taxi: TaxiDetailsUiState) {
        //todo: edit taxi show dialog
        println("on click edit taxi")
    }

    override fun showTaxiMenu(username: String) {
        println("show taxi menu")
        updateState { it.copy(taxiMenu = it.taxiMenu.copy(username = username)) }
    }

    override fun hideTaxiMenu() {
        println("hide taxi menu")
        updateState { it.copy(taxiMenu = it.taxiMenu.copy(username = "")) }
    }
//endregion

    //region export button
    override fun onExportReportClicked() {
        tryToExecute(
            { getTaxiReport.createTaxiReport() },
            { onExportTaxisReportSuccessfully() },
            ::onError
        )
    }

    override fun onDismissExportReportSnackBar() {
        updateState { it.copy(isExportReportSuccessfully = false) }
    }

    private fun onExportTaxisReportSuccessfully() {
        updateState { it.copy(isExportReportSuccessfully = true) }
    }

    //endregion

    override fun onSearchInputChange(searchQuery: String) {
        updateState { it.copy(searchQuery = searchQuery) }
        findTaxiByUsername(searchQuery)
    }


    //region filter dialog
    override fun showFilterMenu() {
        println("show filter")
    }

    override fun hideFilterMenu() {
        println("hide filter")
    }

    override fun onSaveEditTaxiMenu() {
        println("save button, update taxi")
    }

    override fun onCancelEditTaxiMenu() {
        println("cancel button")
//        updateState { it.copy(isEditTaxiMenuVisible = false) }
    }

    //endregion

    //region paging

    override fun onItemsIndicatorChange(itemPerPage: Int) {
       updateState{ it.copy(specifiedUsers = itemPerPage) }
        getDummyTaxiData()
    }

    override fun onPageClick(pageNumber: Int) {
        updateState { it.copy(currentPage = pageNumber) }
        getDummyTaxiData()
        println("page clicked:$pageNumber")
    }

    override fun onTaxiNumberChange(number: Int) {
        updateState { it.copy(taxiNumberInPage = number) }
    }

    //endregion

    //region function new taxi
    private fun getDummyTaxiData() {
        tryToExecute(getTaxis::getTaxis, ::onGetTaxisSuccessfully, ::onError)
    }

    private fun findTaxiByUsername(username: String) {
        tryToExecute(
            { findTaxiByUsername.findTaxiByUsername(username) },
            ::onFindTaxiSuccessfully,
            ::onError
        )
    }

    private fun onFindTaxiSuccessfully(taxis: List<Taxi>) {
        updateState { it.copy(taxis = taxis.toUiState(), isLoading = false) }
    }

    private fun onGetTaxisSuccessfully(taxis: List<Taxi>) {
        updateState { it.copy(taxis = taxis.toUiState(), isLoading = false) }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }

    override fun onCancelCreateTaxiClicked() {
        updateState { it.copy(isAddNewTaxiDialogVisible = false) }
    }

    override fun onTaxiPlateNumberChange(number: String) {
        updateState {
            it.copy(addNewTaxiDialogUiState = it.addNewTaxiDialogUiState.copy(plateNumber = number))
        }
    }

    override fun onDriverUserNamChange(name: String) {
        updateState {
            it.copy(addNewTaxiDialogUiState = it.addNewTaxiDialogUiState.copy(driverUserName = name))
        }
    }

    override fun onCarModelChanged(model: String) {
        updateState { it.copy(addNewTaxiDialogUiState = it.addNewTaxiDialogUiState.copy(carModel = model)) }
    }

    override fun onCarColorSelected(color: CarColor) {
        updateState {
            it.copy(addNewTaxiDialogUiState = it.addNewTaxiDialogUiState.copy(selectedCarColor = color))
        }
    }

    override fun onSeatSelected(seats: Int) {
        updateState { it.copy(addNewTaxiDialogUiState = it.addNewTaxiDialogUiState.copy(seats = seats)) }
    }

    override fun onCreateTaxiClicked() {
        updateState { it.copy(isAddNewTaxiDialogVisible = false) }
        tryToExecute(
            { createNewTaxi.createTaxi(mutableState.value.addNewTaxiDialogUiState.toEntity()) },
            ::onCreateTaxiSuccessfully,
            ::onError
        )
    }

    private fun onCreateTaxiSuccessfully(taxi: Taxi) {
        val newTaxi = mutableState.value.taxis.toMutableList().apply { add(taxi.toUiState()) }
        updateState { it.copy(taxis = newTaxi, isLoading = false) }
    }

    override fun onAddNewTaxiClicked() {
        updateState { it.copy(isAddNewTaxiDialogVisible = true) }
    }
    //endregion

}