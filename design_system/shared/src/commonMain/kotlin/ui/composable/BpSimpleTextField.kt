package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.modifier.noRippleEffect
import com.beepbeep.designSystem.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpSimpleTextField(
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit = {},
    hintColor: Color = Theme.colors.contentTertiary,
    modifier: Modifier = Modifier,
    trailingPainter: Painter? = null,
    leadingPainter: Painter? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    radius: Dp = Theme.radius.medium,
    errorMessage: String = "",
    isError: Boolean = errorMessage.isNotEmpty(),
) {
    Column(
            modifier = modifier,
            horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                value = text,
                placeholder = {
                    Text(
                            hint,
                            style = Theme.typography.body,
                            color = hintColor,
                            modifier = Modifier.fillMaxWidth().noRippleEffect(onClick)
                    )
                },
                onValueChange = onValueChange,
                shape = RoundedCornerShape(radius),
                textStyle = Theme.typography.body.copy(color = Theme.colors.contentPrimary),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                isError = isError,
                trailingIcon = {
                    trailingPainter?.let {
                        Icon(
                                painter = trailingPainter,
                                contentDescription = "trailing icon",
                                tint = Theme.colors.contentTertiary
                        )
                    }
                },
                leadingIcon = {
                    leadingPainter?.let {
                        Icon(
                                painter = leadingPainter,
                                contentDescription = "leading icon",
                                tint = Theme.colors.contentSecondary,
                                modifier = Modifier.noRippleEffect(onClick)
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Theme.colors.surface,
                        unfocusedBorderColor = Theme.colors.contentBorder.copy(alpha = 0.1f),
                        focusedBorderColor = Theme.colors.contentTertiary.copy(alpha = 0.2f),
                        errorBorderColor = Theme.colors.primary.copy(alpha = 0.5f),
                        errorCursorColor = Theme.colors.primary,
                        cursorColor = Theme.colors.contentTertiary,
                ),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Theme.colors.contentTertiary,
                errorCursorColor = Theme.colors.primary,
                focusedBorderColor = Theme.colors.contentTertiary.copy(alpha = 0.2f),
                unfocusedBorderColor = Theme.colors.contentBorder.copy(alpha = 0.1f),
                errorBorderColor = Theme.colors.primary.copy(alpha = 0.5f),
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