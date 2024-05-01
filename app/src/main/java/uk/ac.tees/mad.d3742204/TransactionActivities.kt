// Import statements for necessary Android classes and libraries
package uk.ac.tees.mad.d3742204

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bansim.ui.theme.BansimTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Retrofit service interface for transaction API
interface TransactionApiService {
    @GET("transactions")
    @Headers("Content-Type: application/json")
    suspend fun getTransactions(@Header("Authorization") token: String): TransactionResponse
}

// Model class for transaction response
data class TransactionResponse(
    val message: String,
    val code: Int,
    val data: List<Transaction>
)

// Model class for transaction
data class Transaction(
    val id: Int,
    val userId: Int,
    val amount: Double,
    val type: String,
    val createdAt: Date?,
    val updatedAt: Date?
)

// TransactionsActivity responsible for managing transactions
@ExperimentalMaterial3Api
class TransactionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set content to display the TransactionsScreen
        setContent {
            BansimTheme {
                TransactionsScreen()
            }
        }
    }
}

// Composable function to display the transactions screen UI
@Composable
@ExperimentalMaterial3Api
fun TransactionsScreen() {
    // Coroutine scope for managing coroutines
    val coroutineScope = rememberCoroutineScope()
    // Get the context
    val context = LocalContext.current
    // Create Retrofit instance
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://bansim.iaaws.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // Create API service
    val apiService = retrofit.create(TransactionApiService::class.java)

    // Mutable state to hold transactions
    var transactions by remember { mutableStateOf<List<Transaction>?>(null) }

    // Fetch transactions from API
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val token = getKeyFromSharedPreferences(context, "Token")
            val response = apiService.getTransactions("Bearer $token")
            if(response.code == 401){
                logout(context)
            }
            transactions = response.data

            Log.d("Transactions Activity", "Response Data: $transactions")
        }
    }

    // UI layout for transactions screen
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(bottom = 100.dp)
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
                text = "All Transactions", modifier = Modifier
                    .weight(1f)
                    .padding(start = 100.dp)
            )
        }
    }

    // Display transactions or loading indicator
    if (transactions != null && transactions!!.isNotEmpty()) {
        TransactionsList(transactions!!)
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

// Composable function to display the list of transactions
@Composable
fun TransactionsList(transactions: List<Transaction>) {
    Spacer(modifier = Modifier.height(120.dp))
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 80.dp)
    ) {
        // Transactions List
        LazyColumn {
            items(transactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}

// Composable function to display a single transaction item
@SuppressLint("RememberReturnType")
@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Determine text color based on transaction type
        val textColor = if (transaction.type == "Money In") Color.Green else Color.Red
        // Display transaction type
        Text(
            text = transaction.type,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        // Display transaction amount
        Text(
            text = "£${transaction.amount}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        // Display transaction date if available
        transaction.createdAt?.let { createdAt ->
            Text(
                text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(createdAt),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        // Display user name
        val context = LocalContext.current
        Text(
            text = remember { getKeyFromSharedPreferences(context,"first_name")},
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
    // Divider between transactions
    Divider(modifier = Modifier.fillMaxWidth())
}

// Preview function for TransactionsScreen
@Preview
@Composable
@ExperimentalMaterial3Api
fun PreviewTransactionsScreen() {
    TransactionsScreen()
}