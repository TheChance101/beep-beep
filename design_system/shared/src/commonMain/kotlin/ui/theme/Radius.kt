package com.beepbeep.designSystem.ui.theme

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

data class Radius(
    val small: RoundedCornerShape = RoundedCornerShape(CornerSize(4.dp)),
    val medium: RoundedCornerShape = RoundedCornerShape(CornerSize(8.dp)),
    val large: RoundedCornerShape = RoundedCornerShape(CornerSize(16.dp)),
    val extraLarge: RoundedCornerShape = RoundedCornerShape(CornerSize(24.dp))
)