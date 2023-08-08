package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.colorScheme
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.shapes
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun BeepBeepTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    label: String,
    text: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorMessage: String = "",
    isError: Boolean = errorMessage.isNotEmpty(),
) {
    var showPassword by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = 8.dp),
            color = colorScheme.onPrimary
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = onValueChange,
            shape = shapes.medium,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = colorScheme.onTertiary,
                errorCursorColor = colorScheme.error,
                containerColor = if (!isError) colorScheme.background else colorScheme.secondary,
                errorTrailingIconColor = colorScheme.error,
            ),
            trailingIcon = {
                if (isError) {
                    Icon(
                        imageVector = Icons.Outlined.Error,
                        contentDescription = "error icon",
                        tint = colorScheme.error
                    )
                }
                if ((keyboardType == KeyboardType.Password || keyboardType == KeyboardType.NumberPassword) && !isError) {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                            contentDescription = if (showPassword) "Show Password" else "Hide Password"
                        )
                    }
                }
            },
            visualTransformation =
            if (showPassword){
                VisualTransformation.None
            }
            else if (!(keyboardType == KeyboardType.Password || keyboardType == KeyboardType.NumberPassword)) {
                VisualTransformation.None
            } else{
                PasswordVisualTransformation()
            },
            isError = isError,
        )
        if (isError) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(top = 8.dp),
                color = colorScheme.primary,
            )
        }
    }

}