package org.thechance.common.presentation.taxi

import kotlinx.coroutines.Job
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.usecase.IManageTaxisUseCase
import org.thechance.common.domain.usecase.ITaxiValidationUseCase
import org.thechance.common.domain.util.TaxiStatus
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.restaurant.ErrorWrapper
import org.thechance.common.presentation.util.ErrorState

class TaxiScreenModel(
    private val manageTaxis: IManageTaxisUseCase,
    private val taxiValidation: ITaxiValidationUseCase,
) : BaseScreenModel<TaxiUiState, TaxiUiEffect>(TaxiUiState()), TaxiInteractionListener {

    private var searchJob: Job? = null

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
        updateState { it.copy(pageInfo = taxis.toDetailsUiState(), isLoading = false) }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(isLoading = false) }
        when (error) {
            is ErrorState.InvalidCarType -> {
                updateState {
                    it.copy(
                        newTaxiInfo = it.newTaxiInfo.copy(
                            carModelError = ErrorWrapper(error.errorMessage, true)
                        )
                    )
                }
            }

            is ErrorState.InvalidTaxiPlate -> {
                updateState {
                    it.copy(
                        newTaxiInfo = it.newTaxiInfo.copy(
                            plateNumberError = ErrorWrapper(error.errorMessage, true)
                        )
                    )
                }
            }

            ErrorState.NoConnection -> {
                updateState { it.copy(isNoInternetConnection = true) }
            }

            ErrorState.UnKnownError -> println("error is unknown error: ${error}")
            is ErrorState.InvalidTaxiColor -> println("error is invalid taxi color: ${error.errorMessage}")
            is ErrorState.InvalidTaxiId -> {
                updateState {
                    it.copy(
                        newTaxiInfo = it.newTaxiInfo.copy(
                            driverUserNameError = ErrorWrapper(error.errorMessage, true),
                        )
                    )
                }
                println("error is invalid taxi id: ${error.errorMessage}")
            }

            is ErrorState.SeatOutOfRange -> println("error is seat out of range: ${error.errorMessage}")
            is ErrorState.TaxiAlreadyExists -> println("error is taxi already exists: ${error.errorMessage}")
            is ErrorState.TaxiNotFound -> println("error is taxi not found: ${error.errorMessage}")
            is ErrorState.UserNotExist -> println("error is user not exist: ${error.errorMessage}")
            else -> {}
        }
    }

    private fun clearAddTaxiErrorState() =
        updateState { it.copy(newTaxiInfo = it.newTaxiInfo.copy(
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
        getTaxis()
    }

    override fun onPageClick(pageNumber: Int) {
        updateState { it.copy(currentPage = pageNumber) }
        getTaxis()
    }

    //endregion

    //region add new taxi listener
    override fun onCancelClicked() {
        clearAddNewTaxiDialogState()
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
        tryToExecute(
            { manageTaxis.updateTaxi(mutableState.value.newTaxiInfo.toEntity()) },
            ::onUpdateTaxiSuccessfully,
            ::onError
        )
    }

    private fun onUpdateTaxiSuccessfully(taxi: Taxi) {
        updateState {
            it.copy(
                isAddNewTaxiDialogVisible = false,
                taxiMenu = it.taxiMenu.copy(id = "")
            )
        }
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
        clearAddNewTaxiDialogState()
        getTaxis()
    }

    override fun onAddNewTaxiClicked() {
        clearAddNewTaxiDialogState()
        updateState { it.copy(isAddNewTaxiDialogVisible = true, isEditMode = false) }
    }

    private fun clearAddNewTaxiDialogState() {
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
    override fun showTaxiMenu(taxiId: String) {
        updateState { it.copy(taxiMenu = it.taxiMenu.copy(id = taxiId)) }
    }

    override fun hideTaxiMenu() {
        updateState { it.copy(taxiMenu = it.taxiMenu.copy(id = "")) }
    }

    override fun onDeleteTaxiClicked(taxiId: String) {
        tryToExecute(
            { manageTaxis.deleteTaxi(taxiId = taxiId) },
            ::onDeleteTaxiSuccessfully,
            ::onError
        )
    }

    private fun onDeleteTaxiSuccessfully(result: Boolean) {
//        updateState { it.copy(taxiMenu = it.taxiMenu.copy(id = "")) }
//        mutableState.value.pageInfo.data.find { it.id == taxiId }?.let { taxiDetailsUiState ->
//            val index = mutableState.value.pageInfo.data.indexOf(taxiDetailsUiState)
//            val newTaxi = mutableState.value.pageInfo.data.toMutableList().apply {
//                removeAt(index)
//            }
//            updateState { it.copy(pageInfo = it.pageInfo.copy(data = newTaxi)) }
//        }
        //todo:show snack bar
    }

    override fun onEditTaxiClicked(taxiId: String) {
        updateState { it.copy(isEditMode = true, isAddNewTaxiDialogVisible = true) }
        hideTaxiMenu()
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

//endregion

}