package presentation.map.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.resources.Resources

@Composable
fun FindingRideCard(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 21.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.surface, RoundedCornerShape(8.dp))
                .padding(horizontal = 14.dp),
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp).align(Alignment.CenterHorizontally),
                text = Resources.strings.mapScreenFindingRequest,
                color = Theme.colors.contentPrimary,
                style = Theme.typography.headline,
            )

            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 18.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
                color = Theme.colors.primary
            )
        }
    }
}