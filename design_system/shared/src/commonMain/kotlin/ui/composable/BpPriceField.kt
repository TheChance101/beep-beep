package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpPriceField(
    text: String,
    onValueChange: (String) -> Unit,
    flag: Painter,
    currency: String ,
    modifier: Modifier = Modifier,
    label: String = "",
    hint: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    shapeRadius: Shape = RoundedCornerShape(Theme.radius.medium),
    errorMessage: String = "",
    correctValidation: Boolean = false,
    isError: Boolean = errorMessage.isNotEmpty(),

) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = Theme.dimens.space8),
            style = Theme.typography.title,
            color = Theme.colors.contentPrimary
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            value = text,
            placeholder = {
                Text(
                    hint,
                    style = Theme.typography.caption,
                    color = Theme.colors.contentTertiary
                )
            },
            onValueChange = onValueChange,
            shape = shapeRadius,
            textStyle = Theme.typography.body.copy(Theme.colors.contentPrimary),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = {
                Row(
                    modifier = Modifier.padding(end = Theme.dimens.space8),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8)
                ) {
                    Image(
                        painter = flag,
                        contentDescription = currency,)
                    Text(
                        text = currency,
                        style = Theme.typography.body,
                        color = Theme.colors.contentSecondary
                    )
                }

            },
            isError = isError,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = ContainerColor(isError, correctValidation),
                unfocusedBorderColor = Theme.colors.contentBorder.copy(alpha = 0.1f),
                focusedBorderColor = Theme.colors.contentTertiary.copy(alpha = 0.2f),
                errorBorderColor = Theme.colors.primary.copy(alpha = 0.5f),
                errorCursorColor = Theme.colors.primary,
                cursorColor = Theme.colors.contentTertiary,
            ),
        )

        AnimatedVisibility(isError) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(top = Theme.dimens.space8),
                style = Theme.typography.caption,
                color = Theme.colors.primary
            )
        }
    }
}
@Composable
private fun ContainerColor(isError: Boolean, correctValidation: Boolean): Color {
    return if (isError) {
        Theme.colors.hover
    } else if (correctValidation) {
        Theme.colors.successContainer
    } else {
        Theme.colors.surface
    }
}