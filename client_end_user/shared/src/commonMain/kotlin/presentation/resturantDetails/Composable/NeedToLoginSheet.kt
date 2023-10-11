package presentation.resturantDetails.Composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.theme.Theme
import resources.Resources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeedToLoginSheet(onClick: () -> Unit,text : String) {
    Column (modifier = Modifier.fillMaxWidth()){
        Text(
            text =  text ,
            style= Theme.typography.title,
            modifier = Modifier.padding(top=24.dp,start=16.dp,end=16.dp),
            color= Theme.colors.contentPrimary
        )
        BpButton(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            title = Resources.strings.login,
            onClick = { onClick() },
        )
    }
}
