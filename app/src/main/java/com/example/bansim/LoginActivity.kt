package com.example.bansim
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bansim.ui.theme.BansimTheme

class LoginActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BansimTheme {
                LoginScreen(this)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(activity: ComponentActivity) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.bansimlogo),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .size(width = 125.dp, height = 125.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Hello!",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Text(
                text = "Input your details to login",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            InputGroup("Email","example@gmail.com",true)

            Spacer(modifier = Modifier.height(10.dp))

            InputGroup("Password","password",false)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val intent = Intent(activity, HomeActivity::class.java)
                    activity.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CA6A8))
            ) {
                Text(text = "Sign in")
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun InputGroup(label: String, placeholder: String, keyboard: Boolean) {
    val (textValue, setTextValue) = remember { mutableStateOf("") }
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = textValue,
            onValueChange = setTextValue,
            leadingIcon = { Icon(imageVector = ( if (keyboard) Icons.Default.Email else Icons.Default.Lock), contentDescription = "emailIcon") },
            placeholder = { Text(text = placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .focusable(true),
            keyboardOptions = KeyboardOptions(keyboardType = (if (keyboard) KeyboardType.Email else KeyboardType.Password)),
            visualTransformation = if (!keyboard) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun PreviewLoginScreen() {
    LoginScreen(ComponentActivity())
}