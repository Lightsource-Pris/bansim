package com.example.bansim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(name = "John Doe", totalBalance = "$1000", amount = "$500")
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun HomeScreen(name: String, totalBalance: String, amount: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            BasicText(
                text = "Hello $name",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        BasicText(
            text = "Welcome Back !",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 16.dp)
                .background(color = Color(0xFFBBECED), shape = RoundedCornerShape(3.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Total Balance",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "$amount",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 40.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .clickable {
                                    coroutineScope.launch { navigateToAnotherActivity(context,TransferActivity()) }
                                }
                                .background(color = Color(0xFFFBFBFB), shape = RoundedCornerShape(15.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sendmoney),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(65.dp)
                                    .padding(10.dp)
                                ,
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Send",
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(start=10.dp)
                            )
                            Text(
                                text = "Money",
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(start=10.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "To any account",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(start=10.dp, bottom = 5.dp)
                            )
                        }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .background(color = Color(0xFFFBFBFB), shape = RoundedCornerShape(15.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.senddonation),
                            contentDescription = null,
                            modifier = Modifier
                                .height(65.dp)
                                .padding(10.dp)
                            ,
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Send",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start=10.dp)
                        )
                        Text(
                            text = "Donation",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start=10.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Scanning Code",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start=10.dp, bottom = 5.dp)
                        )
                    }

                }
                Spacer(modifier = Modifier.height(40.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .background(color = Color(0xFFFBFBFB), shape = RoundedCornerShape(15.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.alltransactions),
                            contentDescription = null,
                            modifier = Modifier
                                .height(65.dp)
                                .padding(10.dp)
                            ,
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "All",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start=10.dp)
                        )
                        Text(
                            text = "Transactions",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start=10.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "View all",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start=10.dp, bottom = 5.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .background(color = Color(0xFFFBFBFB), shape = RoundedCornerShape(15.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.allnoti),
                            contentDescription = null,
                            modifier = Modifier
                                .height(65.dp)
                                .padding(10.dp)
                            ,
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "All",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start=10.dp)
                        )
                        Text(
                            text = "Notifications",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start=10.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Check Messages",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start=10.dp, bottom = 5.dp)
                        )
                    }
            }
        }
    }
}

@Preview
@Composable
@ExperimentalMaterial3Api
fun PreviewHomeScreen() {
    HomeScreen(name = "John Doe", totalBalance = "$1000", amount = "$500")
}

@ExperimentalMaterial3Api
fun navigateToAnotherActivity(context: Context, activity: ComponentActivity) {
    val intent = Intent(context, activity::class.java)
    context.startActivity(intent)
}
