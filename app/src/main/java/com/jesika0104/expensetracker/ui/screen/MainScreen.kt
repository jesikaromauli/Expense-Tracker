package com.jesika0104.expensetracker.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jesika0104.expensetracker.R
import com.jesika0104.expensetracker.model.Expense
import com.jesika0104.expensetracker.navigation.Screen
import com.jesika0104.expensetracker.ui.theme.ExpenseTrackerTheme
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.about_app),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    fun shareData(context: Context, message: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    val expenses = remember { mutableStateListOf<Expense>() }

    var titleError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = false
                },
                label = { Text(text = "Name of expenditure") },
                singleLine = true,
                maxLines = 1,
                isError = titleError,
                supportingText = {
                    if (titleError) Text("Title cannot be empty", color = MaterialTheme.colorScheme.error)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                    amountError = false
                },
                label = { Text("Total price") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                singleLine = true,
                maxLines = 1,
                isError = amountError,
                supportingText = {
                    if (amountError) Text("Amount must be a valid number", color = MaterialTheme.colorScheme.error)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (expenses.isNotEmpty()) {
                        val lastExpense = expenses.last()
                        val formattedAmount = NumberFormat.getNumberInstance(Locale("in", "ID")).format(lastExpense.amount)
                        val message = "Baru saja mencatat pengeluaran: ${lastExpense.title}, sebesar Rp $formattedAmount pada ${lastExpense.date}"
                        shareData(context, message)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Share")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(expenses.size) { index ->
                    val expense = expenses[index]
                    val formattedAmount = NumberFormat.getNumberInstance(Locale("in", "ID")).format(expense.amount)
                    Text("${index + 1}. ${expense.title}: Rp $formattedAmount (${expense.date})")
                }
            }
        }

        // FAB Add Button di kanan bawah
        FloatingActionButton(
            onClick = {
                val amountValue = amount.toDoubleOrNull()
                val currentDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

                titleError = title.isBlank()
                amountError = amountValue == null

                if (!titleError && !amountError) {
                    expenses.add(Expense(title, amountValue!!, currentDate))
                    title = ""
                    amount = ""
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    ExpenseTrackerTheme {
        MainScreen(rememberNavController())
    }
}
