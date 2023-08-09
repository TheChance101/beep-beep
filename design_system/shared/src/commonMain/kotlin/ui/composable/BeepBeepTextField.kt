package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.colorScheme
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.dimens
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.shapes
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeepBeepTextField(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    shapeRadius: Shape = shapes.medium,
    errorMessage: String = "",
    correctValidation: Boolean = false,
    isError: Boolean = errorMessage.isNotEmpty(),
) {
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = dimens.space8),
            style = typography.titleLarge,
            color = colorScheme.onPrimary
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            value = text,
            onValueChange = onValueChange,
            shape = shapeRadius,
            textStyle = typography.bodyLarge.copy(colorScheme.onPrimary),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = {
                TrailingIcon(keyboardType, isError, showPassword) {
                    showPassword = !showPassword
                }
            },
            visualTransformation = BeepBeepVisualTransformation(keyboardType, showPassword),
            isError = isError,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = ContainerColor(isError, correctValidation),
                unfocusedBorderColor = colorScheme.outline.copy(alpha = 0.1f),
                focusedBorderColor = colorScheme.onTertiary.copy(alpha = 0.2f),
                errorBorderColor = colorScheme.primary.copy(alpha = 0.5f),
                errorCursorColor = colorScheme.primary,
                cursorColor = colorScheme.outline,
            ),
        )

        if (isError) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(top = dimens.space8),
                style = typography.body,
                color = colorScheme.primary
            )
        }
    }
}

@Composable
private fun ContainerColor(isError: Boolean, correctValidation: Boolean): Color {
    return if (isError) {
        colorScheme.secondary
    } else if (correctValidation) {
        colorScheme.inversePrimary
    } else {
        colorScheme.surface
    }
}

@Composable
fun TrailingIcon(
    keyboardType: KeyboardType,
    isError: Boolean,
    showPassword: Boolean,
    togglePasswordVisibility: () -> Unit
) {
    if ((keyboardType == KeyboardType.Password || keyboardType == KeyboardType.NumberPassword) && !isError) {
        IconButton(onClick = { togglePasswordVisibility() }) {
            Icon(
                imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                contentDescription = if (showPassword) "Show Password" else "Hide Password",
                tint = colorScheme.onTertiary
            )
        }
    }
}

@Composable
private fun BeepBeepVisualTransformation(
    keyboardType: KeyboardType,
    showPassword: Boolean
): VisualTransformation {
    return if (showPassword || keyboardType != KeyboardType.Password && keyboardType != KeyboardType.NumberPassword) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
}
