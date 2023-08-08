package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.colorScheme
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.dimens
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.shapes

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
    isError: Boolean = errorMessage.isNotEmpty(),
) {
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth().padding(dimens.space16),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = dimens.space8),
            color = colorScheme.onPrimary
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = onValueChange,
            shape = shapeRadius,
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
                containerColor = if (isError) colorScheme.primary else colorScheme.surface,
                unfocusedBorderColor = colorScheme.outline,
                focusedBorderColor = colorScheme.onTertiary,
                errorBorderColor = colorScheme.primary,
                errorCursorColor = colorScheme.primary,
                cursorColor = colorScheme.outline,
            )
        )

        if (isError) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(top = dimens.space8),
                color = colorScheme.primary,
            )
        }
    }
}

@Composable
fun TrailingIcon(
    keyboardType: KeyboardType,
    isError: Boolean,
    showPassword: Boolean,
    togglePasswordVisibility: () -> Unit
) {
    if (isError) {
        Icon(
            imageVector = Icons.Outlined.Error,
            contentDescription = "error icon",
            tint = colorScheme.error
        )
    } else if ((keyboardType == KeyboardType.Password || keyboardType == KeyboardType.NumberPassword) && !isError) {
        IconButton(onClick = { togglePasswordVisibility() }) {
            Icon(
                imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                contentDescription = if (showPassword) "Show Password" else "Hide Password"
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
