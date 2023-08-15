package org.thechance.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.thechance.common.ui.screen.scaffold.DashBoardScaffold
import org.thechance.common.ui.screen.scaffold.DashboardAppbar
import org.thechance.common.ui.screen.scaffold.DashboardSideBar

@Composable
fun App() {
    val isDark = remember { mutableStateOf(false) }
    BpTheme(useDarkTheme = isDark.value) {

        DashBoardScaffold(
            appbar = {
                DashboardAppbar("Users", "Mohammad", {})
            },
            sideBar = { DashboardSideBar(onSwitchTheme = {
                isDark.value = !isDark.value
            },darkTheme = isDark.value) },
        ){innerPadding ->
            Column(Modifier.fillMaxSize().padding(innerPadding).background(color = Color.Black)) {  }
        }
    }
}
