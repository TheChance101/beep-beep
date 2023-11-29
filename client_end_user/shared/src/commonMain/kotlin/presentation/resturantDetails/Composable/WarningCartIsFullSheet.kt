package presentation.resturantDetails.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTextButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun WarningCartIsFullDialog(
    onClickGoToCart: () -> Unit,
    onClickClearCart: () -> Unit,
    onDismiss: () -> Unit,
    text: String,
    modifier: Modifier= Modifier,
    isVisitable: Boolean =false,
) {
    if (isVisitable) {
        Box(modifier = Modifier.fillMaxSize()) {
            Dialog(onDismissRequest = onDismiss) {
                Card(
                    modifier = modifier.align(Alignment.Center).zIndex(2f),
                    shape = RoundedCornerShape(Theme.radius.medium),
                    colors = CardDefaults.cardColors(Theme.colors.background)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Icon(
                            modifier = Modifier.padding(vertical = 32.dp).background(
                                color = Theme.colors.warningContainer,
                                shape = RoundedCornerShape(Theme.radius.medium)
                            ).padding(8.dp),
                            painter = painterResource(Resources.images.warningIcon),
                            contentDescription = null,
                            tint = Theme.colors.warning
                        )
                        Text(
                            text = text,
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            color = Theme.colors.contentSecondary,
                            style = Theme.typography.body,
                            textAlign = TextAlign.Center
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 32.dp
                                )
                        ) {
                            BpTextButton(
                                modifier = Modifier.width(144.dp).padding(8.dp),
                                text = Resources.strings.yesIamSure,
                                onClick =  onClickClearCart,
                                heightInDp = 56
                            )
                            BpButton(
                                modifier =    Modifier.width(144.dp).padding(8.dp),
                                title = Resources.strings.goToCart,
                                onClick = onClickGoToCart,
                            )
                        }
                    }
                }
            }
        }
    }
}
