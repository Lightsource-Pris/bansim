// Imports for necessary Android classes and libraries
package uk.ac.tees.mad.d3742204

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

// Retrofit service interface for retrieving user profile data
interface ProfileApiService {
    @GET("profile")
    @Headers("Content-Type: application/json")
    suspend fun getProfile(@Header("Authorization") token: String): ProfileResponse
}

// Data class representing the response from the profile API
data class ProfileResponse(
    val message: String,
    val code: Int,
    val data: ProfileData?
)

// Data class representing the user profile data
data class ProfileData(
    val id: Int,
    val email: String,
    val balance: String,
    val first_name: String,
    val last_name: String,
    val phone: String,
    val account: String,
    val sort_code: String,
    val pin: Int
)

// HomeActivity class responsible for displaying the home screen
@ExperimentalMaterial3Api
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(this)
        }
    }
    // Override onBackPressed method to prevent default behavior
    override fun onBackPressed() {
    }
}

// Composable function for displaying the home screen UI
@Composable
@ExperimentalMaterial3Api
fun HomeScreen(activity: ComponentActivity) {
    // Define coroutine scope for launching coroutines
    val coroutineScope = rememberCoroutineScope()
    // Get the current context
    val context = LocalContext.current
    // Create Retrofit instance for making API requests
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://bansim.iaaws.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // Create API service instance
    val apiService = retrofit.create(ProfileApiService::class.java)

    // Define state variables for storing profile data and user name/balance
    var profileData: ProfileData? by remember { mutableStateOf<ProfileData?>(null) }
    var name by remember { mutableStateOf<String?>(null) }
    var balance by remember { mutableStateOf<String?>(null) }

    // Retrieve user name from shared preferences
    val sname = (remember { getKeyFromSharedPreferences(context, "first_name") }) + " " +remember { getKeyFromSharedPreferences(context, "last_name") }

    // Check if user name is not available or empty
    if(sname == null || sname == "" || sname == " "){
        val token = remember { getKeyFromSharedPreferences(context,"Token") }
        // Use LaunchedEffect to launch coroutine for making API request
        LaunchedEffect(token) {
            coroutineScope.launch {
                val response = apiService.getProfile("Bearer $token")
                if (response.code == 200) {
                    val data = response.data
                    val mess = response.message
                    Log.d("Home Activity", "Response Message: $mess")
                    if (data != null) {
                        // Save profile data to shared preferences
                        saveToPrefs(activity, "account", data.account)
                        saveToPrefs(activity, "first_name", data.first_name)
                        saveToPrefs(activity, "last_name", data.last_name)
                        saveToPrefs(activity, "email", data.email)
                        saveToPrefs(activity, "balance", data.balance)
                        saveToPrefs(activity, "phone", data.phone)
                        profileData = data
                        name = "${data.first_name} ${data.last_name}"
                        balance = data.balance
                    }
                    profileData = response.data
                } else {
                    if (response.code == 401){
                        // Log error if token is expired or invalid
                        Log.e("Home Activity", "Token expired or invalid")
                        // Perform logout action
                        logout(context)
                    }else{
                        // Log error if response code is not successful
                        val message = response.message
                        Log.e("Home Activity", "Error: $message")
                    }
                }

            }
        }
    }else{
        // If user name is available, set name and balance from shared preferences
        name = sname
        balance = remember { getKeyFromSharedPreferences(context, "balance") }
    }

    // Composable UI elements for the home screen
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Image for menu button
            Image(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = null,
                modifier = Modifier.size(24.dp).clickable {
                    coroutineScope.launch { goBack(context, ProfileActivity()) }
                },
            )
            Spacer(modifier = Modifier.width(5.dp))
            // Text displaying user name
            BasicText(
                text = name?:"Israel",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Text displaying welcome message
        BasicText(
            text = "Welcome Back !",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Box for displaying total balance
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 16.dp)
                .background(color = Color(0xFFBBECED), shape = RoundedCornerShape(3.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Text displaying "Total Balance"
                Text(
                    text = "Total Balance",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                // Text displaying user balance
                Text(
                    text = "₤"+(balance?:"0"),
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 40.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        // Row containing buttons for Send Money and All Transactions
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .clickable {
                        coroutineScope.launch { navigateToAnotherActivity(context, TransferActivity()) }
                    }
                    .background(color = Color(0xFFFBFBFB), shape = RoundedCornerShape(15.dp))
            ) {
                // Image for Send Money button
                Image(
                    painter = painterResource(id = R.drawable.sendmoney),
                    contentDescription = null,
                    modifier = Modifier
                        .height(65.dp)
                        .padding(start = 15.dp, top = 10.dp)
                    ,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Text for Send Money button
                Text(
                    text = "Send Money",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start=15.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Text description for Send Money button
                Text(
                    text = "To any account",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start=15.dp, bottom = 5.dp)
                )
            }

        }
        Spacer(modifier = Modifier.height(40.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .clickable {
                        coroutineScope.launch { navigateToAnotherActivity(context, TransactionsActivity()) }
                    }
                    .background(color = Color(0xFFFBFBFB), shape = RoundedCornerShape(15.dp))
            ) {
                // Image for All Transactions button
                Image(
                    painter = painterResource(id = R.drawable.alltransactions),
                    contentDescription = null,
                    modifier = Modifier
                        .height(65.dp)
                        .padding(start = 15.dp, top = 10.dp)
                    ,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Text for All Transactions button
                Text(
                    text = "All Transactions",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start=15.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Text description for All Transactions button
                Text(
                    text = "View all transactions",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start=15.dp, bottom = 5.dp)
                )
            }
        }
    }
}

// Function to retrieve value from shared preferences
fun getKeyFromSharedPreferences(context: Context,key:String): String {
    val sharedPreferences = context.getSharedPreferences("com.example.bansim.PREFS_FILENAME", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, "") ?: ""
}

// Function to navigate to another activity
@ExperimentalMaterial3Api
fun navigateToAnotherActivity(context: Context, activity: ComponentActivity) {
    val intent = Intent(context, activity::class.java)
    context.startActivity(intent)
}

// Function to perform logout action
fun logout(context: Context) {
    val sharedPreferences = context.getSharedPreferences("com.example.bansim.PREFS_FILENAME", Context.MODE_PRIVATE)
    sharedPreferences.edit().clear().apply()
}

// Preview composable for HomeScreen
@Preview
@Composable
@ExperimentalMaterial3Api
fun PreviewHomeScreen() {
    HomeScreen(ComponentActivity())
}