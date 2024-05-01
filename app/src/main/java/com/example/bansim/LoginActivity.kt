// Import statements for necessary Android classes and libraries
package com.example.bansim

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

// Constants
private const val PREFS_FILENAME = "com.example.bansim.PREFS_FILENAME"

// LoginActivity class responsible for managing the login screen
class LoginActivity : ComponentActivity() {
    // Lazy initialization of ApiService instance
    private val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bansim.iaaws.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }

    // Setup of the login screen when the activity is created
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BansimTheme {
                LoginScreen(this, apiService)
            }
        }
    }

    // Method to prevent back navigation
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

    }
}

// Retrofit service interface for login API
interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>
}

// Data class representing the response from the login API
data class LoginResponse(
    val message: String,
    val code: Int,
    val data: LoginData?
)

// Data class representing the login data
data class LoginData(
    val status: Int,
    val token: String
)

// Composable function for displaying the login screen UI
@Composable
@ExperimentalMaterial3Api
fun LoginScreen(activity: ComponentActivity, apiService:ApiService) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

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

            InputGroup("Email", "example@gmail.com", true, email) { email = it }
            Spacer(modifier = Modifier.height(10.dp))
            InputGroup("Password", "password", false, password) { password = it }

            Spacer(modifier = Modifier.height(20.dp))
            val intent = Intent(activity, HomeActivity::class.java)
            Button(
                onClick = {
                    val tag = "LoginActivity"
                    Log.d(tag, "Button clicked")
                    if (isValidInput(email, password)) {
                        Log.d(tag, "Attempting login...")
                        scope.launch(Dispatchers.IO) {
                            try {
                                val response = apiService.login(email, password)
                                val data = response.body()
                                Log.d(tag, "Response Data: $data")
                                if (response.body()?.code == 200) {
                                    val token = response.body()?.data?.token ?: ""
                                    Log.d(tag, "Login successful. Token: $token")
                                    saveToPrefs(activity, "Token", token)
                                    activity.startActivity(intent)
                                } else {
                                    val errorMessage = response.body()?.message
                                    Log.e(tag, "Login failed: $errorMessage")
                                    showToast(activity, "Login failed: $errorMessage")
                                }
                            } catch (e: Exception) {
                                // Handle network errors
                                val errorMessage = e.message
                                Log.e(tag, "Network error: $errorMessage")
                                showToast(activity, "Network error: ${e.message}")
                            }
                        }
                    } else {
                        Log.d(tag, "Invalid input")
                        showToast(activity, "Please fill in valid email and password")
                    }
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

// Composable function for input fields group
@Composable
@ExperimentalMaterial3Api
fun InputGroup(label: String, placeholder: String, keyboard: Boolean, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = { Icon(imageVector = (if (keyboard) Icons.Default.Email else Icons.Default.Lock), contentDescription = "emailIcon") },
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

// Function to validate input email and password
private fun isValidInput(email: String, password: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 8
}

// Function to display a toast message
private fun showToast(activity: ComponentActivity, message: String) {
    activity.runOnUiThread {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}

// Function to save data to shared preferences
fun saveToPrefs(context: Context, key: String, value: String) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    prefs.edit().putString(key, value).apply()
}

// Preview composable for LoginScreen
@Preview
@ExperimentalMaterial3Api
@Composable
fun PreviewLoginScreen() {
    // Mock ApiService for preview
    val mockApiService = object : ApiService {
        override suspend fun login(email: String, password: String): Response<LoginResponse> {
            return Response.success(LoginResponse("Success", 200, LoginData(1, "mock_token")))
        }
    }
    LoginScreen(ComponentActivity(), mockApiService)
}