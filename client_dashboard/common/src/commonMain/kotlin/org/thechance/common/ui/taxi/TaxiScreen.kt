package org.thechance.common.ui.screen.taxi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen

object TaxiScreen : Screen {
    @Composable
    override fun Content() {
        TaxiContent(
            onClickBack = {}
        )
    }

    @Composable
    private fun TaxiContent(
        onClickBack: () -> Unit,
    ) {

        Column(
            Modifier.fillMaxSize().background(color = Color.Gray),
        ) {
            Box(Modifier.weight(1f)) {
                Text(text = "Taxi Screen")

            }
        }
    }
}