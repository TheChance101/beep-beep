package presentation.cuisines

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.usecase.GetCuisinesUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.base.ErrorState.NoInternet
import resources.strings.IStringResources

class CuisinesScreenModel(
    private val getCuisinesUseCase: GetCuisinesUseCase,
    private val strResources: IStringResources
) : BaseScreenModel<CuisinesUiState, CuisinesUiEffect>(CuisinesUiState()),
    CuisinesInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getDate()
    }

    private fun getDate() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            getCuisinesUseCase::getCuisines,
            ::onGetCuisinesSuccess,
            ::onError
        )
    }

    private fun onGetCuisinesSuccess(cuisines: List<Cuisine>) {
        updateState {
            it.copy(
                error = "",
                isLoading = false,
                cuisines = cuisines.toCuisineUiState()
            )
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(isLoading = false) }
        when (errorState) {
            is NoInternet -> {
                updateState { it.copy(error = strResources.noInternet) }
            }

            else -> {
                updateState { it.copy(error = strResources.unknownError) }
            }
        }
    }

    // region interactions
    override fun onCuisineClicked(cuisineId: String) {
        sendNewEffect(CuisinesUiEffect.NavigateToCuisineDetails(cuisineId))
    }

    override fun onBackIconClicked() {
        sendNewEffect(CuisinesUiEffect.NavigateBack)
    }
    // endregion
}