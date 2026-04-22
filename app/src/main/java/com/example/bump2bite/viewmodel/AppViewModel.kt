package com.example.bump2bite.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class Language {
    ENGLISH, KANNADA
}

data class UserProfile(
    val name: String = "",
    val age: String = "",
    val trimester: String = "1",
    val city: String = "",
    val language: Language = Language.ENGLISH
)

class AppViewModel : ViewModel() {
    var userProfile by mutableStateOf(UserProfile())
    var isLoggedIn by mutableStateOf(false)
    var currentLanguage by mutableStateOf(Language.ENGLISH)
    var riskScore by mutableStateOf(45) // Moderate default

    fun login(email: String, pass: String) {
        // Simple logic for demo
        isLoggedIn = true
    }

    fun signup(profile: UserProfile) {
        userProfile = profile
        currentLanguage = profile.language
        isLoggedIn = true
    }

    fun logout() {
        isLoggedIn = false
    }

    fun toggleLanguage() {
        currentLanguage = if (currentLanguage == Language.ENGLISH) Language.KANNADA else Language.ENGLISH
    }
}
