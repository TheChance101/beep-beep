package org.thechance.common.presentation.composables.table

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.util.Constants

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TotalItemsIndicator(
    modifier: Modifier = Modifier,
    totalItems: Int,
    maxNumberOfItems: Int = Constants.PAGE_MAX_NUMBER_OF_ITEMS,
    numberItemInPage: Int,
    onItemPerPageChange: (Int) -> Unit,
    itemType: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space16)
    ) {
        BasicTextField(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Theme.colors.contentBorder,
                    shape = RoundedCornerShape(Theme.radius.medium)
                ).padding(vertical = Theme.dimens.space8).width(Theme.dimens.space40),
            value = numberItemInPage.toString(),
            onValueChange = {
                runCatching {
                    onItemPerPageChange(
                        if (it.toInt() >= maxNumberOfItems) maxNumberOfItems else it.toInt()
                    )
                }
            },
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                color = Theme.colors.contentPrimary,
                fontStyle = Theme.typography.title.fontStyle
            ),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Text(
            "$itemType out of $totalItems ${itemType}s",
            style = Theme.typography.body,
            color = Theme.colors.contentSecondary
        )
    }
}
