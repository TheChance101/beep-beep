package org.thechance.common.presentation.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
inline val Int.kms: Dp get() = (this * 0.75).dp

@Stable
inline val Double.kms: Dp get() = (this * 0.75).dp