package com.example.bump2bite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Composable
fun LoginScreen(viewModel: AppViewModel, onNavigateToSignup: () -> Unit, onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bump2Bite",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = TealPrimary
        )
        Text(text = "Login to your account", color = Color.Gray)

        Spacer(modifier = Modifier.height(40.dp))

        AppTextField(value = email, onValueChange = { email = it }, label = "Email / Phone")
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(value = password, onValueChange = { password = it }, label = "Password", isPassword = true)

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { /* Forgot Password */ }, modifier = Modifier.align(Alignment.End)) {
            Text("Forgot Password?", color = TealPrimary)
        }

        Spacer(modifier = Modifier.height(32.dp))

        AppButton(text = "Login", onClick = {
            viewModel.login(email, password)
            onLoginSuccess()
        })

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Don't have an account?")
            TextButton(onClick = onNavigateToSignup) {
                Text("Sign Up", color = TealPrimary, fontWeight = FontWeight.Bold)
            }
        }
    }
}
