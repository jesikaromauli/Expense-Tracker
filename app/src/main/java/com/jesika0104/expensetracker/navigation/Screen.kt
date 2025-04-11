package com.jesika0104.expensetracker.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object Result: Screen("resultScreen")
}