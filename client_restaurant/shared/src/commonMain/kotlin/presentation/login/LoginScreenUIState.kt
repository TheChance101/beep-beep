package presentation.login

import presentation.composable.ModalBottomSheetState

data class LoginScreenUIState(
    val restaurantName: String = "",
    val description: String = "",
    val email: String = "",
    val ownerEmail: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val sheetState: ModalBottomSheetState = ModalBottomSheetState(),
    val showPermissionSheet: Boolean = false,
    val error: String = ""
)
