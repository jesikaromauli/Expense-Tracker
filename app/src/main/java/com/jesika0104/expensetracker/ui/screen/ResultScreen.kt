package com.jesika0104.expensetracker.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesika0104.expensetracker.model.Expense
import com.jesika0104.expensetracker.ui.theme.ExpenseTrackerTheme
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(expenses: List<Expense>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Expense Result")
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (expenses.isEmpty()) {
                Text("No expenses recorded.", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn {
                    items(expenses.size) { index ->
                        val expense = expenses[index]
                        val formattedAmount = NumberFormat.getNumberInstance(Locale("in", "ID")).format(expense.amount)
                        Text("${index + 1}. ${expense.title}: Rp $formattedAmount (${expense.date})")
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ResultScreenPreview() {
    val sampleExpenses = listOf(
        Expense("Makan", 15000.0, "10 Apr 2025"),
        Expense("Transport", 25000.0, "11 Apr 2025")
    )
    ExpenseTrackerTheme {
        ResultScreen(sampleExpenses)
    }
}
