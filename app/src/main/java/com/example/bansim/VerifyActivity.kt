package com.example.bansim
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bansim.ui.theme.BansimTheme
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class VerifyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BansimTheme {
                VerifyScreen()
            }
        }
    }
}
@ExperimentalMaterial3Api
@Composable
fun VerifyScreen() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        coroutineScope.launch { goBack(context, TransferActivity()) }
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Verify Transaction", modifier = Modifier
                .weight(1f)
                .padding(start = 90.dp))
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center

    ) {
        Spacer(modifier = Modifier.height(60.dp))

        val (textValue, setTextValue) = remember { mutableStateOf("") }
        Column {
            Text(text = "Enter your account pin",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(
                value = textValue,
                onValueChange = setTextValue,
                placeholder = { Text(text = "input pin") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .focusable(true)
                    .border(border = BorderStroke(2.dp, Color.Black)),
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                visualTransformation = VisualTransformation.None
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                val intent = Intent(context, SuccessActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CA6A8)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = "PROCEED", color = Color.White)
        }
    }
}


@Preview
@ExperimentalMaterial3Api
@Composable
fun PreviewVerifyScreen() {
    VerifyScreen()
}
