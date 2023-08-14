package org.thechance.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BpTheme
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.ui.screen.DashBoardScaffold
import org.thechance.common.ui.screen.DashboardAppbar
import org.thechance.common.ui.screen.DashboardSideBar

@Composable
fun App() {
    BpTheme(useDarkTheme = false) {
        DashBoardScaffold(
            appbar = {
                DashboardAppbar("Users", "Mohammad", {})
            },
            sideBar = { DashboardSideBar() },
        ){innerPadding ->
            Column(Modifier.fillMaxSize().padding(innerPadding).background(color = Color.Black)) {  }
        }
    }
}
