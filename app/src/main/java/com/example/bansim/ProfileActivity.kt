// Import statements for necessary Android classes and libraries
package com.example.bansim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bansim.ui.theme.BansimTheme
import kotlinx.coroutines.launch

// ProfileActivity class responsible for managing the profile screen
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

// Composable function for displaying the profile screen UI
@ExperimentalMaterial3Api
@Composable
fun ProfileScreen() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    // Column layout for organizing profile information
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Row for header containing back button and title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            // Back button
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
            // Title
            Text(
                text = "Profile", modifier = Modifier
                    .weight(1f)
                    .padding(start = 130.dp)
            )
        }
    }
    // Column layout for displaying user profile details
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Spacer for vertical spacing
        Spacer(modifier = Modifier.height(60.dp))
        // Mutable state for holding text value
        val (textValue, setTextValue) = remember { mutableStateOf("") }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // User avatar
            Image(
                painter = painterResource(id = R.drawable.mepassport),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .padding(top = 10.dp)
                    .size(width = 125.dp, height = 125.dp)
            )
            // User first name
            Text(
                text = remember { getKeyFromSharedPreferences(context, "first_name") },
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
            // User last name
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = remember { getKeyFromSharedPreferences(context, "last_name") },
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
            // User email
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = remember { getKeyFromSharedPreferences(context, "email") },
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            // User phone number
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = remember { getKeyFromSharedPreferences(context, "phone") },
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            // Spacer for vertical spacing
            Spacer(modifier = Modifier.height(30.dp))
            // Logout button
            Text(
                text = "LOGOUT",
                Modifier.clickable {
                    logout(context)
                    coroutineScope.launch { goBack(context, LoginActivity()) }
                },
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = Color.Red
            )
        }
    }
}

// Preview composable for ProfileScreen
@Preview
@ExperimentalMaterial3Api
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}