package com.example.bansim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterial3Api
class TransactionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BansimTheme {
                TransactionsScreen(transactions)
            }
        }
    }
}

data class Transaction(
    val type: String,
    val amount: Double,
    val date: Date,
    val recipientOrSender: String
)

val transactions = listOf(
    Transaction("Money In", 100.0, Date(), "John"),
    Transaction("Money Out", 50.0, Date(), "Alice")
)

@Composable
@ExperimentalMaterial3Api
fun TransactionsScreen(transactions: List<Transaction>) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
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
                text = "All Transactions", modifier = Modifier
                    .weight(1f)
                    .padding(start = 100.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        // Transactions List
        LazyColumn {
            items(transactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}


@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textColor = if (transaction.type == "Money In") Color.Green else Color.Red
        Text(
            text = transaction.type,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "£${transaction.amount}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(transaction.date),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = transaction.recipientOrSender,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
    Divider(modifier = Modifier.fillMaxWidth())
}

@Preview
@Composable
@ExperimentalMaterial3Api
fun PreviewTransactionsScreen() {
    TransactionsScreen(transactions)
}