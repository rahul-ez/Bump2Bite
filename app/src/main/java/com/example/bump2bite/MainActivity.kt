package com.example.bump2bite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bump2bite.ui.screens.DashboardScreen
import com.example.bump2bite.ui.screens.PostpartumScreen
import com.example.bump2bite.ui.screens.SplashScreen
import com.example.bump2bite.ui.screens.TipsScreen
import com.example.bump2bite.ui.screens.WelcomeScreen
import com.example.bump2bite.ui.theme.Bump2BiteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bump2BiteTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToWelcome = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onContinue = {
                    navController.navigate(Screen.Dashboard.route)
                }
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToTips = {
                    navController.navigate(Screen.Tips.route)
                }
            )
        }
        composable(Screen.Tips.route) {
            TipsScreen(
                onBack = { navController.popBackStack() },
                onNavigateToPostpartum = {
                    navController.navigate(Screen.Postpartum.route)
                }
            )
        }
        composable(Screen.Postpartum.route) {
            PostpartumScreen()
        }
    }
}
