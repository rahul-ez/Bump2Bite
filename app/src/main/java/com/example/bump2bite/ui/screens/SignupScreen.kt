package com.example.bump2bite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bump2bite.ui.components.AppButton
import com.example.bump2bite.ui.components.AppTextField
import com.example.bump2bite.ui.theme.TealPrimary
import com.example.bump2bite.viewmodel.AppViewModel
import com.example.bump2bite.viewmodel.Language
import com.example.bump2bite.viewmodel.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(viewModel: AppViewModel, onSignupSuccess: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var trimester by remember { mutableStateOf("1") }
    var language by remember { mutableStateOf(Language.ENGLISH) }
    
    var expandedTrimester by remember { mutableStateOf(false) }
    var expandedLang by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "Create Profile",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TealPrimary
        )
        Spacer(modifier = Modifier.height(24.dp))

        AppTextField(value = name, onValueChange = { name = it }, label = "Full Name")
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(value = age, onValueChange = { age = it }, label = "Age")
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(value = city, onValueChange = { city = it }, label = "City/Town")
        
        Spacer(modifier = Modifier.height(16.dp))

        // Trimester Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedTrimester,
            onExpandedChange = { expandedTrimester = !expandedTrimester }
        ) {
            OutlinedTextField(
                value = "Trimester $trimester",
                onValueChange = {},
                readOnly = true,
                label = { Text("Current Trimester") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTrimester) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(
                expanded = expandedTrimester,
                onDismissRequest = { expandedTrimester = false }
            ) {
                listOf("1", "2", "3").forEach { t ->
                    DropdownMenuItem(
                        text = { Text("Trimester $t") },
                        onClick = {
                            trimester = t
                            expandedTrimester = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Language Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedLang,
            onExpandedChange = { expandedLang = !expandedLang }
        ) {
            OutlinedTextField(
                value = if (language == Language.ENGLISH) "English" else "Kannada",
                onValueChange = {},
                readOnly = true,
                label = { Text("Preferred Language") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLang) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(
                expanded = expandedLang,
                onDismissRequest = { expandedLang = false }
            ) {
                DropdownMenuItem(
                    text = { Text("English") },
                    onClick = { language = Language.ENGLISH; expandedLang = false }
                )
                DropdownMenuItem(
                    text = { Text("Kannada") },
                    onClick = { language = Language.KANNADA; expandedLang = false }
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        AppButton(text = "Sign Up", onClick = {
            viewModel.signup(UserProfile(name, age, trimester, city, language))
            onSignupSuccess()
        })
    }
}
