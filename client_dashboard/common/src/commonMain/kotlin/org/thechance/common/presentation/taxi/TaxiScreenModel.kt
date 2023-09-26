package org.thechance.common.presentation.taxi

import kotlinx.coroutines.Job
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.usecase.IManageTaxisUseCase
import org.thechance.common.domain.util.TaxiStatus
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.restaurant.ErrorWrapper
import org.thechance.common.presentation.util.ErrorState

class TaxiScreenModel(
    private val manageTaxis: IManageTaxisUseCase
) : BaseScreenModel<TaxiUiState, TaxiUiEffect>(TaxiUiState()), TaxiInteractionListener {

    private var searchJob: Job? = null
    private var limitJob: Job? = null

    init {
        getTaxis()
    }

    override fun onSearchInputChange(searchQuery: String) {
        updateState { it.copy(searchQuery = searchQuery) }
        launchSearchJob()
    }

    private fun launchSearchJob() {
        searchJob?.cancel()
        searchJob = launchDelayed(300L) { getTaxis() }
    }

    private fun getTaxis() {
        tryToExecute(
            {
                manageTaxis.getTaxis(
                    state.value.searchQuery.trim(),
                    state.value.taxiFilterUiState.toEntity(),
                    state.value.currentPage,
                    state.value.specifiedTaxis
                )
            },
            ::onGetTaxisSuccessfully,
            ::onError
        )
    }

    private fun onGetTaxisSuccessfully(taxis: DataWrapper<Taxi>) {
        updateState { it.copy(pageInfo = taxis.toDetailsUiState(), isLoading = false, hasConnection = true) }
        if (state.value.currentPage > state.value.pageInfo.totalPages) {
            onPageClick(state.value.pageInfo.totalPages)
        }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(isLoading = false) }
        when (error) {
            is ErrorState.MultipleErrors -> {
                val errorStates = error.errors
                updateState {
                    it.copy(
                        newTaxiInfo = it.newTaxiInfo.copy(
                            plateNumberError = errorStates.firstInstanceOfOrNull<ErrorState.InvalidTaxiPlate>()
                                ?.let { error ->
                                    ErrorWrapper(error.errorMessage, true)
                                },
                            driverUserNameError = errorStates.firstInstanceOfOrNull<ErrorState.InvalidUserName>()
                                ?.let { error ->
                                    ErrorWrapper(error.errorMessage, true)
                                },
                            carModelError = errorStates.firstInstanceOfOrNull<ErrorState.InvalidCarType>()
                                ?.let { error ->
                                    ErrorWrapper(error.errorMessage, true)
                                },
                        )
                    )
                }
            }

            is ErrorState.NoConnection -> {
                updateState { it.copy(hasConnection = false) }
            }

            is ErrorState.TaxiAlreadyExists -> {
                updateState {
                    it.copy(
                        newTaxiInfo = it.newTaxiInfo.copy(
                            plateNumberError = ErrorWrapper(error.errorMessage, true)
                        )
                    )
                }
            }

            else -> {}
        }

    }

    private fun clearAddTaxiErrorState() =
        updateState {
            it.copy(
                newTaxiInfo = it.newTaxiInfo.copy(
                    plateNumberError = ErrorWrapper(),
                    carModelError = ErrorWrapper(),
                    driverUserNameError = ErrorWrapper(),
                )
            )
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
        launchLimitJob()
    }

    private fun launchLimitJob() {
        limitJob?.cancel()
        limitJob = launchDelayed(300L) { getTaxis() }
    }

    override fun onPageClick(pageNumber: Int) {
        updateState { it.copy(currentPage = pageNumber) }
        getTaxis()
    }

    //endregion

    //region add new taxi listener
    override fun onCancelClicked() {
        clearTaxiInfoState()
        updateState { it.copy(isAddNewTaxiDialogVisible = false) }
    }

    override fun onTaxiPlateNumberChange(number: String) {
        updateState {
            it.copy(
                newTaxiInfo = it.newTaxiInfo.copy(
                    plateNumber = number, isFormValid = number.isNotEmpty()
                )
            )
        }
    }

    override fun onDriverUserNamChange(name: String) {
        updateState {
            it.copy(
                newTaxiInfo = it.newTaxiInfo.copy(
                    driverUserName = name, isFormValid = name.isNotEmpty()
                )
            )
        }
    }

    override fun onCarModelChanged(model: String) {
        updateState {
            it.copy(
                newTaxiInfo = it.newTaxiInfo.copy(
                    carModel = model, isFormValid = model.isNotEmpty()
                )
            )
        }
    }

    override fun onCarColorSelected(color: CarColor) {
        updateState { it.copy(newTaxiInfo = it.newTaxiInfo.copy(selectedCarColor = color)) }
    }

    override fun onSeatSelected(seats: Int) {
        updateState { it.copy(newTaxiInfo = it.newTaxiInfo.copy(seats = seats)) }
    }

    override fun onSaveClicked() {
        val newTaxi = mutableState.value.newTaxiInfo
        tryToExecute(
            { manageTaxis.updateTaxi(newTaxi.toEntity(), newTaxi.id) },
            ::onUpdateTaxiSuccessfully,
            ::onError
        )
    }

    private fun onUpdateTaxiSuccessfully(taxi: Taxi) {
        updateState { it.copy(isAddNewTaxiDialogVisible = false) }
        setTaxiMenuVisibility(taxi.id, false)
        mutableState.value.pageInfo.data.find { it.id == taxi.id }?.let { taxiDetailsUiState ->
            val index = mutableState.value.pageInfo.data.indexOf(taxiDetailsUiState)
            val newTaxi = mutableState.value.pageInfo.data.toMutableList().apply {
                set(index, taxi.toDetailsUiState())
            }
            updateState { it.copy(pageInfo = it.pageInfo.copy(data = newTaxi)) }
            //todo:show snack bar
        }
    }

    override fun onCreateTaxiClicked() {
        clearAddTaxiErrorState()
        tryToExecute(
            { manageTaxis.createTaxi(mutableState.value.newTaxiInfo.toEntity()) },
            ::onCreateTaxiSuccessfully,
            ::onError
        )
    }

    private fun onCreateTaxiSuccessfully(taxi: Taxi) {
        updateState { it.copy(isAddNewTaxiDialogVisible = false) }
        val newTaxi =
            mutableState.value.taxis.toMutableList().apply { add(taxi.toDetailsUiState()) }
        updateState { it.copy(taxis = newTaxi, isLoading = false) }
        clearTaxiInfoState()
        getTaxis()
    }

    override fun onAddNewTaxiClicked() {
        clearTaxiInfoState()
        updateState { it.copy(isAddNewTaxiDialogVisible = true, isEditMode = false) }
    }

    override fun onRetry() {
        getTaxis()
    }

    private fun clearTaxiInfoState() {
        updateState {
            it.copy(
                newTaxiInfo = it.newTaxiInfo.copy(
                    plateNumber = "",
                    driverUserName = "",
                    carModel = "",
                    selectedCarColor = CarColor.WHITE,
                    seats = 1,
                    isFormValid = false,
                    plateNumberError = ErrorWrapper(),
                    carModelError = ErrorWrapper(),
                ),
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

    override fun onClearAllClicked() {
        updateState {
            it.copy(
                taxiFilterUiState = TaxiFilterUiState(
                    carColor = null,
                    seats = -1,
                    status = null
                )
            )
        }
    }

    override fun onCancelFilterClicked() {
        onFilterMenuDismiss()
    }

    override fun onSaveFilterClicked() {
        updateState { it.copy(isFilterDropdownMenuExpanded = false) }
        tryToExecute(
            {
                manageTaxis.getTaxis(
                    username = state.value.searchQuery,
                    taxiFiltration = mutableState.value.taxiFilterUiState.toEntity(),
                    page = mutableState.value.currentPage,
                    limit = mutableState.value.specifiedTaxis
                )
            },
            ::onFilterTaxiSuccessfully,
            ::onError
        )
    }

    private fun onFilterTaxiSuccessfully(taxis: DataWrapper<Taxi>) {
        updateState { it.copy(pageInfo = taxis.toDetailsUiState(), isLoading = false) }
    }

    //endregion

    //region taxi menu listener

    override fun onShowTaxiMenu(id: String) {
        setTaxiMenuVisibility(id, true)
    }

    override fun onHideTaxiMenu(id: String) {
        setTaxiMenuVisibility(id, false)
    }

    override fun onDeleteTaxiClicked(taxiId: String) {
        tryToExecute(
            { manageTaxis.deleteTaxi(taxiId = taxiId) },
            ::onDeleteTaxiSuccessfully,
            ::onError
        )
        setTaxiMenuVisibility(taxiId, false)
    }

    private fun onDeleteTaxiSuccessfully(taxi: Taxi) {
        setTaxiMenuVisibility(taxi.id, false)
        getTaxis()
    }

    override fun onEditTaxiClicked(taxiId: String) {
        updateState { it.copy(isEditMode = true, isAddNewTaxiDialogVisible = true) }
        setTaxiMenuVisibility(taxiId, false)
        tryToExecute(
            { manageTaxis.getTaxiById(taxiId) },
            ::onGetTaxiByIdSuccess,
            ::onError
        )
    }

    private fun onGetTaxiByIdSuccess(taxi: Taxi) {
        val taxiState = taxi.toUiState()
        updateState { it.copy(newTaxiInfo = taxiState) }
    }

    private fun setTaxiMenuVisibility(id: String, isExpanded: Boolean) {
        val currentTaxisState = state.value.pageInfo.data
        val selectedTaxiState = currentTaxisState.first { it.id == id }
        val updatedTaxiState = selectedTaxiState.copy(isTaxiMenuExpanded = isExpanded)
        updateState {
            it.copy(
                pageInfo = state.value.pageInfo.copy(data = currentTaxisState.toMutableList()
                    .apply { set(indexOf(selectedTaxiState), updatedTaxiState) })
            )
        }
    }
//endregion

}