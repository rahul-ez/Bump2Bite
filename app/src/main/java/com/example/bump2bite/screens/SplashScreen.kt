package com.example.bump2bite.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bump2bite.ui.theme.SoftTeal
import com.example.bump2bite.ui.theme.TealPrimary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToWelcome: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        onNavigateToWelcome()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, SoftTeal)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp),
                tint = TealPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Bump2Bite",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TealPrimary
            )
            Text(
                text = "Smart Smiles from Pregnancy to Baby Teeth",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
