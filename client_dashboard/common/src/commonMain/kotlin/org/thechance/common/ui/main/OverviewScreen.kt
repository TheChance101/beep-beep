package org.thechance.common.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.beepbeep.designSystem.ui.composable.BpButton


object OverviewScreen : Screen {
    @Composable
    override fun Content() {
        val navigate = LocalNavigator.currentOrThrow

        OverviewContent(onClickBack = { navigate.popUntilRoot()
            println("screens:${navigate.items.last()}")

        })

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OverviewContent(
    onClickBack: () -> Unit,
) {

    Column(
        Modifier.fillMaxSize().background(color = Color.Red),
    ) {
        Box(Modifier.weight(1f)) {
            Text(text = "Main Screen")
                BpButton(
                    onClick = onClickBack,
                    modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                    title = "Back"
                )

        }
    }
}


