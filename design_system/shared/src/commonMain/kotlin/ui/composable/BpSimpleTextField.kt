package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpSimpleTextField(
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    trailingPainter: Painter? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    radius: Dp = Theme.radius.medium,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().height(56.dp),
        value = text,
        placeholder = {
            Text(
                hint,
                style = Theme.typography.caption,
                color = Theme.colors.contentTertiary
            )
        },
        onValueChange = onValueChange,
        shape = RoundedCornerShape(radius),
        textStyle = Theme.typography.body.copy(color = Theme.colors.contentPrimary),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = {
            trailingPainter?.let {
                Icon(
                    painter = trailingPainter,
                    contentDescription = "trailing icon",
                    tint = Theme.colors.contentTertiary
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Theme.colors.surface,
            unfocusedBorderColor = Theme.colors.contentBorder.copy(alpha = 0.1f),
            focusedBorderColor = Theme.colors.contentTertiary.copy(alpha = 0.2f),
            cursorColor = Theme.colors.contentTertiary,
        ),
    )

}