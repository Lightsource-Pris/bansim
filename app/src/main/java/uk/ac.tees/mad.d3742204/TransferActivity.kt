package uk.ac.tees.mad.d3742204

// Import necessary Android classes and libraries
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bansim.ui.theme.BansimTheme
import kotlinx.coroutines.launch

// TransferActivity responsible for managing transfer functionality
@ExperimentalMaterial3Api
class TransferActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set content to display the TransferScreen
        setContent {
            BansimTheme {
                TransferScreen(this)
            }
        }
    }
}

// Composable function to display the transfer screen UI
@ExperimentalMaterial3Api
@Composable
fun TransferScreen(activity: ComponentActivity) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // State for input fields
    val accountHolderState = remember { mutableStateOf("") }
    val accountNumberState = remember { mutableStateOf("") }
    val bankNameState = remember { mutableStateOf("") }
    val sortCodeState = remember { mutableStateOf("") }
    val amountState = remember { mutableStateOf("") }

    // UI layout for transfer screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
                    .clickable {
                        coroutineScope.launch { goBack(context, HomeActivity()) }
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "New Transfer", modifier = Modifier.weight(1f).padding(start = 90.dp))
        }
        Spacer(modifier = Modifier.height(60.dp))

        // Input fields for transfer details
        TransferInput(label = "Account Holder", placeholder = "Enter account holder name", state = accountHolderState)
        Spacer(modifier = Modifier.height(40.dp))
        TransferInput(label = "Account Number", placeholder = "Enter account number", state = accountNumberState)
        Spacer(modifier = Modifier.height(40.dp))
        TransferInput(label = "Bank Name", placeholder = "Enter bank name", state = bankNameState)
        Spacer(modifier = Modifier.height(40.dp))
        TransferInput(label = "Sort Code", placeholder = "Enter sort code", state = sortCodeState)
        Spacer(modifier = Modifier.height(40.dp))
        TransferInput(label = "Amount", placeholder = "Enter amount", state = amountState, keyboardType = KeyboardOptions.Default, visualTransformation = VisualTransformation.None)

        Spacer(modifier = Modifier.height(40.dp))

        // Proceed button
        Button(
            onClick = {
                if (validateFields(accountHolderState.value, accountNumberState.value, bankNameState.value, sortCodeState.value, amountState.value)) {
                    val intent = Intent(activity, VerifyActivity::class.java)
                    activity.startActivity(intent)
                } else {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CA6A8)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = "PROCEED", color = Color.White)
        }
    }
}

// Composable function to display an input field for transfer details
@ExperimentalMaterial3Api
@Composable
fun TransferInput(
    label: String,
    placeholder: String,
    state: MutableState<String>, // Add this parameter
    keyboardType: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column {
        Text(text = label)
        // Text field for input
        OutlinedTextField(
            value = state.value,
            onValueChange = { state.value = it },
            placeholder = { Text(text = placeholder) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .focusable(true)
                .border(border = BorderStroke(2.dp, Color.Black)),
            textStyle = MaterialTheme.typography.bodyLarge,
            keyboardOptions = keyboardType,
            visualTransformation = visualTransformation
        )
    }
}

// Preview function for TransferScreen
@Preview
@Composable
@ExperimentalMaterial3Api
fun PreviewTransferScreen() {
    TransferScreen(ComponentActivity())
}

// Function to navigate back to the previous screen
@ExperimentalMaterial3Api
fun goBack(context: Context, activity: ComponentActivity) {
    val intent = Intent(context, activity::class.java)
    context.startActivity(intent)
}

// Function to validate input fields
fun validateFields(accountHolder: String, accountNumber: String, bankName: String, sortCode: String, amount: String): Boolean {
    return accountHolder.isNotBlank() && accountNumber.isNotBlank() && bankName.isNotBlank() && sortCode.isNotBlank() && amount.isNotBlank()
}