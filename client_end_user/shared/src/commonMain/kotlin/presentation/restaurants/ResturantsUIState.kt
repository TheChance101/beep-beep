package presentation.restaurants

import presentation.base.ErrorState
import presentation.composable.ModalBottomSheetState

data class RestaurantsUIState(
    val isFavourite: Boolean = false,
    val isLoading: Boolean = false,
    val isAddToCartLoading: Boolean = false,
    val isLogin: Boolean = false,
    val sheetState: ModalBottomSheetState = ModalBottomSheetState(),
    val sheetMealState: ModalBottomSheetState = ModalBottomSheetState(),
    val showLoginSheet: Boolean = false,

    val showMealSheet: Boolean = false,
    val showToast: Boolean = false,

    val error: ErrorState? = null,
    val errorAddToCart: ErrorState? = null,
)



