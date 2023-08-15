package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme.colors
import com.beepbeep.designSystem.ui.theme.Theme.dimens
import com.beepbeep.designSystem.ui.theme.Theme.radius
import com.beepbeep.designSystem.ui.theme.Theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpTextField(
    label: String,
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    shapeRadius: Shape = RoundedCornerShape(radius.medium),
    errorMessage: String = "",
    correctValidation: Boolean = false,
    isError: Boolean = errorMessage.isNotEmpty(),
) {
    var showPassword by remember { mutableStateOf(false) }

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
            modifier = Modifier.fillMaxWidth().height(56.dp),
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
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = {
                TrailingIcon(keyboardType, isError, showPassword) {
                    showPassword = !showPassword
                }
            },
            visualTransformation = BpVisualTransformation(keyboardType, showPassword),
            isError = isError,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = ContainerColor(isError, correctValidation),
                unfocusedBorderColor = colors.contentBorder.copy(alpha = 0.1f),
                focusedBorderColor = colors.contentTertiary.copy(alpha = 0.2f),
                errorBorderColor = colors.primary.copy(alpha = 0.5f),
                errorCursorColor = colors.primary,
                cursorColor = colors.contentTertiary,
            ),
        )

        AnimatedVisibility(isError) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(top = dimens.space8),
                style = typography.caption,
                color = colors.primary
            )
        }
    }
}

@Composable
private fun ContainerColor(isError: Boolean, correctValidation: Boolean): Color {
    return if (isError) {
        colors.hover
    } else if (correctValidation) {
        colors.successContainer
    } else {
        colors.surface
    }
}

@Composable
private fun TrailingIcon(
    keyboardType: KeyboardType,
    isError: Boolean,
    showPassword: Boolean,
    togglePasswordVisibility: () -> Unit
) {
    AnimatedVisibility((keyboardType == KeyboardType.Password || keyboardType == KeyboardType.NumberPassword) && !isError) {
        IconButton(onClick = { togglePasswordVisibility() }) {
            Icon(
                imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                contentDescription = if (showPassword) "Show Password" else "Hide Password",
                tint = colors.contentTertiary
            )
        }
    }
}

@Composable
private fun BpVisualTransformation(
    keyboardType: KeyboardType,
    showPassword: Boolean
): VisualTransformation {
    return if (showPassword || keyboardType != KeyboardType.Password && keyboardType != KeyboardType.NumberPassword) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
}