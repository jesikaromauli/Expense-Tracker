package com.jesika0104.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jesika0104.expensetracker.ui.screen.AboutScreen
import com.jesika0104.expensetracker.ui.screen.MainScreen
import com.jesika0104.expensetracker.ui.screen.ResultScreen
import com.jesika0104.expensetracker.viewModel.ExpenseViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController = rememberNavController(),
    expenseViewModel: ExpenseViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController = navController, expenseViewModel = expenseViewModel)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController = navController)
        }
        composable(route = Screen.Result.route) {
            ResultScreen(
                navController = navController,
                expenseViewModel = expenseViewModel
            )
        }
    }
}
