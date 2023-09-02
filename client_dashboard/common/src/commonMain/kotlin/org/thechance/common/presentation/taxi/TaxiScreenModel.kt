package org.thechance.common.presentation.taxi

import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.usecase.*
import org.thechance.common.domain.util.TaxiStatus
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class TaxiScreenModel(
    private val manageTaxis: IManageTaxisUseCase,
    private val findTaxisByUsername: ISearchTaxisByUserNameUseCase,
    private val filterTaxi: IFilterTaxisUseCase
) : BaseScreenModel<TaxiUiState, TaxiUiEffect>(TaxiUiState()), TaxiInteractionListener {

    init {
        getDummyTaxiData()
    }

    override fun onSearchInputChange(searchQuery: String) {
        updateState { it.copy(searchQuery = searchQuery) }
        findTaxisByUsername(searchQuery)
    }

    private fun getDummyTaxiData() {
        tryToExecute(
            { manageTaxis.getTaxis(state.value.currentPage, state.value.specifiedTaxis) },
            ::onGetTaxisSuccessfully, ::onError
        )
    }

    private fun onGetTaxisSuccessfully(taxis: DataWrapper<Taxi>) {
        updateState { it.copy(pageInfo = taxis.toUiState(), isLoading = false) }
    }

    private fun findTaxisByUsername(username: String) {
        tryToExecute(
            {
                findTaxisByUsername.searchTaxisByUsername(
                    username,
                    state.value.currentPage,
                    state.value.specifiedTaxis
                )
            },
            ::onFindTaxiSuccessfully,
            ::onError
        )
    }

    private fun onFindTaxiSuccessfully(taxis: DataWrapper<Taxi>) {
        updateState {
            it.copy(pageInfo = taxis.toUiState(), isLoading = false)
        }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }

    //region export listener

    override fun onExportReportClicked() {
        tryToExecute(
            { manageTaxis.createTaxiReport() },
            { onExportTaxisReportSuccessfully() },
            ::onError
        )
    }

    private fun onExportTaxisReportSuccessfully() {
        updateState { it.copy(isReportExportedSuccessfully = true) }
    }

    override fun onDismissExportReportSnackBar() {
        updateState { it.copy(isReportExportedSuccessfully = false) }
    }

    //endregion

    //region paging listener

    override fun onItemsIndicatorChange(itemPerPage: Int) {
        updateState { it.copy(specifiedTaxis = itemPerPage) }
        getDummyTaxiData()
    }

    override fun onPageClick(pageNumber: Int) {
        updateState { it.copy(currentPage = pageNumber) }
        getDummyTaxiData()
    }

    //endregion

    //region add new taxi listener
    override fun onCancelCreateTaxiClicked() {
        updateState { it.copy(isAddNewTaxiDialogVisible = false) }
        clearAddNewTaxiDialogState()
    }

    override fun onTaxiPlateNumberChange(number: String) {
        updateState {
            it.copy(newTaxiInfo = it.newTaxiInfo.copy(plateNumber = number))
        }
    }

    override fun onDriverUserNamChange(name: String) {
        updateState {
            it.copy(newTaxiInfo = it.newTaxiInfo.copy(driverUserName = name))
        }
    }

    override fun onCarModelChanged(model: String) {
        updateState { it.copy(newTaxiInfo = it.newTaxiInfo.copy(carModel = model)) }
    }

    override fun onCarColorSelected(color: CarColor) {
        updateState {
            it.copy(newTaxiInfo = it.newTaxiInfo.copy(selectedCarColor = color))
        }
    }

    override fun onSeatSelected(seats: Int) {
        updateState { it.copy(newTaxiInfo = it.newTaxiInfo.copy(seats = seats)) }
    }

    override fun onCreateTaxiClicked() {
        updateState { it.copy(isAddNewTaxiDialogVisible = false) }
        tryToExecute(
            { manageTaxis.createTaxi(mutableState.value.newTaxiInfo.toEntity()) },
            ::onCreateTaxiSuccessfully,
            ::onError
        )
    }

    private fun onCreateTaxiSuccessfully(taxi: Taxi) {
        val newTaxi = mutableState.value.taxis.toMutableList().apply { add(taxi.toUiState()) }
        updateState { it.copy(taxis = newTaxi, isLoading = false) }
        clearAddNewTaxiDialogState()
    }

    override fun onAddNewTaxiClicked() {
        updateState { it.copy(isAddNewTaxiDialogVisible = true) }
    }

    private fun clearAddNewTaxiDialogState() {
        updateState {
            it.copy(
                newTaxiInfo = it.newTaxiInfo.copy(
                    plateNumber = "",
                    driverUserName = "",
                    carModel = "",
                    selectedCarColor = CarColor.WHITE,
                    seats = 1
                )
            )
        }
    }

    //endregion

    //region filter menu listener

    override fun onFilterMenuDismiss() {
        updateState { it.copy(isFilterDropdownMenuExpanded = false) }
    }

    override fun onFilterMenuClicked() {
        updateState { it.copy(isFilterDropdownMenuExpanded = true) }
    }

    override fun onSelectedCarColor(color: CarColor) {
        updateState {
            it.copy(taxiFilterUiState = it.taxiFilterUiState.copy(carColor = color))
        }
    }

    override fun onSelectedSeat(seats: Int) {
        updateState {
            it.copy(taxiFilterUiState = it.taxiFilterUiState.copy(seats = seats))
        }
    }

    override fun onSelectedStatus(status: TaxiStatus) {
        updateState {
            it.copy(taxiFilterUiState = it.taxiFilterUiState.copy(status = status))
        }
    }

    override fun onCancelFilterClicked() {
        updateState { it.copy(
            isFilterDropdownMenuExpanded = false,
            taxiFilterUiState = it.taxiFilterUiState.copy(
            carColor = CarColor.WHITE, seats = 1, status = TaxiStatus.ONLINE)
        ) }
        getDummyTaxiData()
    }

    override fun onSaveFilterClicked() {
        updateState { it.copy(isFilterDropdownMenuExpanded = false) }
        tryToExecute(
            {
                filterTaxi(
                    taxi = mutableState.value.taxiFilterUiState.toEntity(),
                    page = mutableState.value.currentPage,
                    numberOfTaxis = mutableState.value.specifiedTaxis
                )
            },
            ::onFilterTaxiSuccessfully,
            ::onError
        )
    }

    private fun onFilterTaxiSuccessfully(taxis: DataWrapper<Taxi>) {
        updateState { it.copy(pageInfo = taxis.toUiState(), isLoading = false) }
    }

    //endregion

    //region taxi menu listener
    override fun showTaxiMenu(username: String) {
        updateState { it.copy(taxiMenu = it.taxiMenu.copy(username = username)) }
    }

    override fun hideTaxiMenu() {
        updateState { it.copy(taxiMenu = it.taxiMenu.copy(username = "")) }
    }

    override fun onDeleteTaxiClicked(taxi: TaxiDetailsUiState) {
        println("delete taxi")
        //todo: delete taxi
    }

    override fun onEditTaxiClicked(taxi: TaxiDetailsUiState) {
        //todo: edit taxi show dialog
        println("on click edit taxi")
    }

    override fun onSaveEditTaxiMenu() {
        //todo: save taxi
        println("save button, update taxi")
    }

    override fun onCancelEditTaxiMenu() {
        //todo: cancel edit taxi
        println("cancel button")
    }

//endregion

}