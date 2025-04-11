package com.jesika0104.expensetracker.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jesika0104.expensetracker.viewModel.ExpenseViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    navController: NavHostController,
    expenseViewModel: ExpenseViewModel
) {
    val context = LocalContext.current
    val expenses = expenseViewModel.expenses
    val total = expenses.sumOf { it.amount }

    val numberFormat = NumberFormat.getNumberInstance(Locale("in", "ID"))
    numberFormat.maximumFractionDigits = 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Expense Results") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (expenses.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No expenses added.")
                }
            } else {
                expenses.forEach { expense ->
                    Text(text = "${expense.title}: Rp${numberFormat.format(expense.amount)}")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Total: Rp${numberFormat.format(total)}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    if (total > 0) {
                        shareData(context, "My total expenses: Rp${numberFormat.format(total)}")
                    } else {
                        Toast.makeText(context, "No data to share.", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Share Total")
                }
            }
        }
    }
}