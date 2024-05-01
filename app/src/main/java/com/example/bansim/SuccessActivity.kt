// Import statements for necessary Android classes and libraries
package com.example.bansim

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bansim.ui.theme.BansimTheme
import kotlinx.coroutines.delay

// SuccessActivity class responsible for managing the success screen
@ExperimentalMaterial3Api
class SuccessActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        // Set content to display the SuccessScreen
        setContent {
            BansimTheme {
                SuccessScreen()
            }
        }
    }

    // Override onBackPressed() to prevent going back from this screen
    override fun onBackPressed() {
        // Do nothing
    }
}

// Composable function to display the success screen UI
@Composable
@ExperimentalMaterial3Api
private fun SuccessScreen() {
    // Animatable to control alpha value for fading effect
    val alpha = remember {
        Animatable(0f)
    }
    // Get the context
    val context = LocalContext.current
    // Intent to navigate to HomeActivity
    val intent = Intent(context, HomeActivity::class.java)

    // UI layout for the success screen
    Box(modifier= Modifier
        .fillMaxSize()
        .background(
            Color(0xFF4CA6A8)
        )
        , contentAlignment = Alignment.Center) {
        // Success image
        Image(
            modifier = Modifier
                .size(width = 80.dp, height = 80.dp), // Adjust width and height as needed
            painter = painterResource(id = R.drawable.sent),
            contentDescription = null
        )
        // Text "Money Sent"
        Text(text = "Money Sent", color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(top = 140.dp)
        )
        // Text "Successfully"
        Text(text = "Successfully", color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(top = 190.dp)
        )
    }

    // Animation effect using LaunchedEffect
    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000)
        )
        delay(2000)
        // Redirect to HomeActivity after delay
        context.startActivity(intent)
    }
}

// Preview function for SuccessScreen
@Preview
@ExperimentalMaterial3Api
@Composable
fun PreviewSuccessScreen() {
    SuccessScreen()
}