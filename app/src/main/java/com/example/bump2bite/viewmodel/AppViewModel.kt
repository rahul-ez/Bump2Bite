package com.example.bump2bite.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

enum class Language {
    ENGLISH, KANNADA
}

data class UserProfile(
    val name: String = "",
    val age: String = "",
    val expectedDeliveryDate: String = "",
    val isDelivered: Boolean = false,
    val city: String = "",
    val language: Language = Language.ENGLISH,
)

class AppViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    var userProfile by mutableStateOf<UserProfile?>(null)
    var isLoggedIn by mutableStateOf(auth.currentUser != null)
    var currentLanguage by mutableStateOf(Language.ENGLISH)
    var riskScore by mutableIntStateOf(45) // Moderate default
    var authError by mutableStateOf<String?>(null)
    var isSavingAssessment by mutableStateOf(value = false)
    var calculatedRiskLevel by mutableStateOf("Moderate Risk")

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
                    
                    // Save profile to Firestore
                    auth.currentUser?.uid?.let { uid ->
                        db.collection("users").document(uid).set(profile)
                    }
                    
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

    fun saveAssessment(responses: List<Int>, questions: List<String>, onComplete: () -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        isSavingAssessment = true

        // Simple scoring logic based on index (0 is usually best/low risk in this questionnaire)
        val score = (responses.sum().toFloat() / (responses.size.toFloat() * 2f)) * 100f
        riskScore = score.toInt()

        calculatedRiskLevel = when {
            score < 33 -> "Low Risk"
            score < 66 -> "Moderate Risk"
            else -> "High Risk"
        }

        val assessmentData = hashMapOf(
            "responses" to responses.mapIndexed { index, selectedIdx ->
                mapOf(
                    "question" to questions[index],
                    "selectedOptionIndex" to selectedIdx,
                )
            },
            "score" to score,
            "riskLevel" to calculatedRiskLevel,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("users")
            .document(uid)
            .collection("assessments")
            .add(assessmentData)
            .addOnSuccessListener {
                isSavingAssessment = false
                onComplete()
            }
            .addOnFailureListener {
                isSavingAssessment = false
                // Handle error
            }
    }
}
