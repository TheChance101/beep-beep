package org.thechance.common.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

@Composable
fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }