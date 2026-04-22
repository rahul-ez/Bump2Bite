package com.example.bump2bite

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object Dashboard : Screen("dashboard")
    object Tips : Screen("tips")
    object Postpartum : Screen("postpartum")
}
