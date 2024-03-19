package com.example.bansim
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.compose.animation.core.Animatable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            BansimTheme {
                SplashScreen()
            }
        }
    }

    @Preview
    @Composable
    private fun SplashScreen() {
        val alpha = remember {
            Animatable(0f)
        }
        LaunchedEffect(key1 = true, block = {
            alpha.animateTo(1f,
            animationSpec = tween(1500)
            )
            delay(2000)
            startActivity(Intent(this@SplashScreenActivity,MainActivity::class.java))
        })
        Box(modifier= Modifier
            .fillMaxSize()
            .background(
                Color(0xFFFFFFFF)
            )
            , contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier
                    .alpha(alpha.value)
                    .size(width = 250.dp, height = 250.dp), // Adjust width and height as needed
                painter = painterResource(id = R.drawable.bansim),
                contentDescription = null
            )
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