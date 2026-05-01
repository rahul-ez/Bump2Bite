package com.example.bump2bite.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

enum class Language {
    ENGLISH, KANNADA
}

data class UserProfile(
    val name: String = "",
    val age: String = "",
    val expectedDeliveryDate: String = "",
    val isDelivered: Boolean = false,
    val city: String = "",
    val language: Language = Language.ENGLISH
)

class AppViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    
    var userProfile by mutableStateOf<UserProfile?>(null)
    var isLoggedIn by mutableStateOf(auth.currentUser != null)
    var currentLanguage by mutableStateOf(Language.ENGLISH)
    var riskScore by mutableIntStateOf(45) // Moderate default
    var authError by mutableStateOf<String?>(null)

    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        authError = null
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isLoggedIn = true
                    onSuccess()
                } else {
                    authError = task.exception?.message ?: "Login failed"
                }
            }
    }

    fun signup(email: String, pass: String, profile: UserProfile, onSuccess: () -> Unit) {
        authError = null
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userProfile = profile
                    currentLanguage = profile.language
                    isLoggedIn = true
                    // In a real app, you'd save the profile to Firestore here
                    onSuccess()
                } else {
                    authError = task.exception?.message ?: "Signup failed"
                }
            }
    }

    fun logout() {
        auth.signOut()
        isLoggedIn = false
    }

    fun toggleLanguage() {
        currentLanguage = if (currentLanguage == Language.ENGLISH) Language.KANNADA else Language.ENGLISH
    }
}
