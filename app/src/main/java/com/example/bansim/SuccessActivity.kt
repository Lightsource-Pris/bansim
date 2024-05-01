package com.example.bansim
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

@ExperimentalMaterial3Api
class SuccessActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            BansimTheme {
                SuccessScreen()
            }
        }
    }
}

@Composable
@ExperimentalMaterial3Api
private fun SuccessScreen() {
    val alpha = remember {
        Animatable(0f)
    }
    val context = LocalContext.current
    val intent = Intent(context, HomeActivity::class.java)

    Box(modifier= Modifier
        .fillMaxSize()
        .background(
            Color(0xFF4CA6A8)
        )
        , contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier
                .size(width = 80.dp, height = 80.dp), // Adjust width and height as needed
            painter = painterResource(id = R.drawable.sent),
            contentDescription = null
        )
        Text(text = "Money Sent", color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(top = 140.dp)
        )
        Text(text = "Successfully", color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(top = 190.dp)
        )
    }

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(3000)
        )
        delay(3000)
        context.startActivity(intent)
    }
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun PreviewSuccessScreen() {
    SuccessScreen()
}

