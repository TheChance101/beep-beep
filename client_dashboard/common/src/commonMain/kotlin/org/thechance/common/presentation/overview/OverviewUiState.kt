package org.thechance.common.presentation.overview


data class OverviewUiState(
    val isLoading: Boolean = false,
    val users: List<User> = userlist,
    val dropdownMenuState: DropdownMenuState = DropdownMenuState(),
)

data class User(
    val name: String,
    val image: String,
    val role: String,
)

data class DropdownMenuState(
    val isExpanded: Boolean = false,
    val items: List<String> = listOf("Daily", "Weekly", "Monthly"),
    val selectedIndex: Int = 0,
)

val userlist = listOf(
    User("Aa", "dummy_img.png", "Admin"),
    User("Bb", "dummy_img.png", "Admin"),
    User("Cc", "dummy_img.png", "Admin"),
    User("Dd", "dummy_img.png", "Admin"),
)


