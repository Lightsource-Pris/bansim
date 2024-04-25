package com.example.bansim
import android.content.Context
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bansim.ui.theme.BansimTheme
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class TransferActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BansimTheme {
                TransferScreen()
            }
        }
    }
}
@ExperimentalMaterial3Api
@Composable
fun TransferScreen() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
                    .clickable {
                        coroutineScope.launch { goBack(context,HomeActivity()) }
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "New Transfer", modifier = Modifier.weight(1f).padding(start = 90.dp))
        }
        Spacer(modifier = Modifier.height(60.dp))

        TransferInput(label = "Account Holder", placeholder = "Enter account holder name")
        Spacer(modifier = Modifier.height(40.dp))
        TransferInput(label = "Account Number", placeholder = "Enter account number")
        Spacer(modifier = Modifier.height(40.dp))
        TransferInput(label = "Bank Name", placeholder = "Enter bank name")
        Spacer(modifier = Modifier.height(40.dp))
        TransferInput(label = "Sort Code", placeholder = "Enter sort code")
        Spacer(modifier = Modifier.height(40.dp))
        TransferInput(label = "Amount", placeholder = "Enter amount", keyboardType = KeyboardOptions.Default, visualTransformation = VisualTransformation.None)

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {  },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CA6A8)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = "PROCEED", color = Color.White)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun TransferInput(label: String, placeholder: String, keyboardType: KeyboardOptions = KeyboardOptions.Default, visualTransformation: androidx.compose.ui.text.input.VisualTransformation = androidx.compose.ui.text.input.VisualTransformation.None) {
    val (textValue, setTextValue) = remember { mutableStateOf("") }
    Column {
        Text(text = label)
        OutlinedTextField(
            value = textValue,
            onValueChange = setTextValue,
            placeholder = { Text(text = placeholder) },
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
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun PreviewTransferScreen() {
    TransferScreen()
}

@ExperimentalMaterial3Api
fun goBack(context: Context, activity: ComponentActivity) {
    val intent = Intent(context, activity::class.java)
    context.startActivity(intent)
}