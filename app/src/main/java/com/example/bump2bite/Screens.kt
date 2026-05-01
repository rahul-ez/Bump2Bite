package com.example.bump2bite

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object Test : Screen("test")
    object Tracker : Screen("tracker")
    object Tips : Screen("tips")
    object Profile : Screen("profile")
    object Postpartum : Screen("postpartum")
}
