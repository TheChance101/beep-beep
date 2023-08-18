package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme.colors
import com.beepbeep.designSystem.ui.theme.Theme.dimens
import com.beepbeep.designSystem.ui.theme.Theme.radius
import com.beepbeep.designSystem.ui.theme.Theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpExpandedTextField(
    label: String,
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    shapeRadius: Shape = RoundedCornerShape(radius.medium),
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = dimens.space8),
            style = typography.title,
            color = colors.contentPrimary
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(104.dp),
            value = text,
            placeholder = {
                Text(
                    hint,
                    style = typography.caption,
                    color = colors.contentTertiary
                )
            },
            onValueChange = onValueChange,
            shape = shapeRadius,
            textStyle = typography.body.copy(colors.contentPrimary),
            singleLine = false,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = colors.surface,
                unfocusedBorderColor = colors.divider.copy(alpha = 0.1f),
                focusedBorderColor = colors.contentTertiary.copy(alpha = 0.2f),
                cursorColor = colors.contentTertiary,
            ),
        )
    }
}
