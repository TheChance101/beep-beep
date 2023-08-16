package org.thechance.common.ui.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen


object UserScreen : Screen {
    @Composable
    override fun Content() {
        UserContent(
            onClickBack = {}
        )
    }

    @Composable
    private fun UserContent(
        onClickBack: () -> Unit,
    ) {

        Column(
            Modifier.fillMaxSize().background(color = Color.Blue),
        ) {
            Box(Modifier.weight(1f)) {
                Text(text = "User Screen")

            }
        }
    }
}