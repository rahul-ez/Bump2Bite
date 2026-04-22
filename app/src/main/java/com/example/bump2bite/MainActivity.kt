package com.example.bump2bite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bump2bite.ui.screens.*
import com.example.bump2bite.ui.theme.Bump2BiteTheme
import com.example.bump2bite.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bump2BiteTheme {
                val appViewModel: AppViewModel = viewModel()
                AppNavigation(appViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: AppViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = viewModel,
                onNavigateToSignup = { navController.navigate(Screen.Signup.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Signup.route) {
            SignupScreen(
                viewModel = viewModel,
                onSignupSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToTest = { navController.navigate(Screen.Test.route) },
                onNavigateToTips = { navController.navigate(Screen.Tips.route) },
                onNavigateToProfile = { /* Navigate to profile */ },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onTabSelected = { route ->
                    if (route != "home") {
                        navController.navigate(route)
                    }
                }
            )
        }
        composable(Screen.Test.route) {
            TestScreen(
                viewModel = viewModel,
                onTestComplete = {
                    navController.navigate(Screen.Tips.route) {
                        popUpTo(Screen.Test.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Tracker.route) {
            TrackerScreen(
                viewModel = viewModel,
                onTabSelected = { route ->
                    if (route != "tracker") {
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route)
                        }
                    }
                }
            )
        }
        composable(Screen.Tips.route) {
            TipsScreen(
                viewModel = viewModel,
                onTabSelected = { route ->
                    if (route != "tips") {
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route)
                        }
                    }
                }
            )
        }
    }
}
