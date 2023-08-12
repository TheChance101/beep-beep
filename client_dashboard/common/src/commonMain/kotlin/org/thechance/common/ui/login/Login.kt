package org.thechance.common.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beepbeep.designSystem.ui.composable.BeepBeepButton
import com.beepbeep.designSystem.ui.composable.BeepBeepCheckBoxDraft
import com.beepbeep.designSystem.ui.composable.BeepBeepTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent() {
    Row(
        Modifier.fillMaxSize().padding(top = 40.dp, start = 40.dp, bottom = 40.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(Modifier.weight(.7f)){
            Image(
                painter = painterResource("login_image.png"),
                contentDescription = null,
                alignment = Alignment.CenterStart,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center){
            Column(
                Modifier.fillMaxHeight().width(350.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text("Login", fontSize = 24.sp, color = Color.Black.copy(.87f))
                Text(
                    "Use admin account to login",
                    fontSize = 16.sp,
                    color = Color.Black.copy(.38f),
                    modifier = Modifier.padding(top = 8.dp)
                )
                BeepBeepTextField(
                    onValueChange = { },
                    text = "",
                    label = "Username",
                    modifier = Modifier.padding(top = 16.dp)
                )
                BeepBeepTextField(
                    onValueChange = { },
                    text = "",
                    label = "Password",
                    keyboardType = KeyboardType.Password,
                    modifier = Modifier.padding(top = 16.dp)
                )
                BeepBeepCheckBoxDraft(
                    text = "Keep me logged in",
                    isChecked = false,
                    onCheck = {},
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                )
                BeepBeepButton(
                    onClick = {},
                    modifier = Modifier.padding(top = 24.dp).fillMaxWidth()
                ) {
                    Text("Login")
                }
            }
        }
    }
}