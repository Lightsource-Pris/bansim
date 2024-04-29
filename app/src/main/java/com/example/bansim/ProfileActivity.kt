package com.example.bansim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bansim.ui.theme.BansimTheme
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BansimTheme {
                ProfileScreen()
            }
        }
    }
}
@ExperimentalMaterial3Api
@Composable
fun ProfileScreen() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
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
                        coroutineScope.launch { goBack(context, HomeActivity()) }
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Profile", modifier = Modifier
                    .weight(1f)
                    .padding(start = 130.dp)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Spacer(modifier = Modifier.height(60.dp))

        val (textValue, setTextValue) = remember { mutableStateOf("") }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Image(
                painter = painterResource(id = R.drawable.bansimlogo),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .size(width = 125.dp, height = 125.dp)
            )
            Text(
                text = "Israel",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "Akin-Akinsanya",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "israelakinakinsany@gmail.com",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "07778727920",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "LOGOUT",
                Modifier.clickable {
                    coroutineScope.launch { goBack(context, LoginActivity()) }
                },
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = Color.Red
            )
        }


    }
}


@Preview
@ExperimentalMaterial3Api
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}
