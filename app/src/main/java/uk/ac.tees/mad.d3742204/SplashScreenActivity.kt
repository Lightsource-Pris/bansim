// Import statements for necessary Android classes and libraries
package uk.ac.tees.mad.d3742204

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bansim.ui.theme.BansimTheme
import kotlinx.coroutines.delay

// Disable lint warning for custom splash screen
@SuppressLint("CustomSplashScreen")
// SplashScreenActivity class responsible for managing the splash screen
class SplashScreenActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        // Set content to display the SplashScreen
        setContent {
            BansimTheme {
                SplashScreen()
            }
        }
    }

    // Check if the user is already logged in when the activity resumes
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onResume() {
        super.onResume()
        // Get token from SharedPreferences
        val token = getKeyFromSharedPreferences(this, "Token")
        // If token is not null or empty, redirect to HomeActivity
        if (!token.isNullOrEmpty()) {
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }

    // Preview function for SplashScreen
    @Preview
    @Composable
    private fun SplashScreen() {
        // Animatable to control alpha value for fading effect
        val alpha = remember {
            Animatable(0f)
        }
        // Animation effect using LaunchedEffect
        LaunchedEffect(key1 = true, block = {
            alpha.animateTo(1f,
                animationSpec = tween(1500)
            )
            delay(2000)
            // Redirect to LoginActivity after splash screen delay
            startActivity(Intent(this@SplashScreenActivity,LoginActivity::class.java))
        })
        // UI layout for the splash screen
        Box(modifier= Modifier
            .fillMaxSize()
            .background(
                Color(0xFFFFFFFF)
            )
            , contentAlignment = Alignment.Center) {
            // Bansim logo image
            Image(
                modifier = Modifier
                    .size(width = 250.dp, height = 250.dp), // Adjust width and height as needed
                painter = painterResource(id = R.drawable.bansim),
                contentDescription = null
            )
            // Text "Bansim" with different styles for each part
            Text(text = AnnotatedString.Builder().apply {
                pushStyle(SpanStyle(
                    color = Color(0xFF4CA6A8),
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                ))
                append("Ban")
                pop()
                pushStyle(SpanStyle(color = Color(0xFF000000),
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                ))
                append("sim")
            }.toAnnotatedString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center).padding(top = 100.dp)
            )
        }
    }
}