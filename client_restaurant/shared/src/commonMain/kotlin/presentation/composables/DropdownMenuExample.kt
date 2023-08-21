package presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }

//    DropdownMenu(
//        expanded = expanded,
//        onDismissRequest = { expanded = false }
//    ) {
//        DropdownMenuItem(onClick = { /* Do something */ }) {
//            Text("Option 1")
//        }
//        DropdownMenuItem(onClick = { /* Do something */ }) {
//            Text("Option 2")
//        }
//        DropdownMenuItem(onClick = { /* Do something */ }) {
//            Text("Option 3")
//        }
//    }
}