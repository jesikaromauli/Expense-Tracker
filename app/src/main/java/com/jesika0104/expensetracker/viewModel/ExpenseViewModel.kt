package com.jesika0104.expensetracker.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.jesika0104.expensetracker.model.Expense

class ExpenseViewModel : ViewModel() {

    // List pengeluaran, hanya bisa diubah dari dalam ViewModel
    private val _expenses = mutableStateListOf<Expense>()
    val expenses: List<Expense> get() = _expenses

    // Fungsi untuk menambahkan pengeluaran
    fun addExpense(expense: Expense) {
        _expenses.add(expense)
    }
}
