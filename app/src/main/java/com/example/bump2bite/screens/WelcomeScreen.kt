package com.example.bump2bite.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bump2bite.components.AppButton
import com.example.bump2bite.ui.theme.SoftTeal
import com.example.bump2bite.ui.theme.TealPrimary

@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    var dueDate by remember { mutableStateOf("1x") }
    var brushingFreq by remember { mutableStateOf(true) }
    var fluorideUse by remember { mutableStateOf(true) }
    var weeklyFreq by remember { mutableStateOf("1-2 weeks") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Welcome, Sarah!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TealPrimary
        )
        Text(
            text = "Please enter your details to assess caries risk.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Illustration placeholder
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SoftTeal)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Illustration of Mother & Baby", color = TealPrimary)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Expected due date", fontWeight = FontWeight.SemiBold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("1x", "2x", "3x", "Sx").forEach { option ->
                FilterChip(
                    selected = dueDate == option,
                    onClick = { dueDate = option },
                    label = { Text(option) },
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Brushing frequency", fontWeight = FontWeight.SemiBold)
            Switch(checked = brushingFreq, onCheckedChange = { brushingFreq = it })
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Fluoride toothpaste use", fontWeight = FontWeight.SemiBold)
            Switch(checked = fluorideUse, onCheckedChange = { fluorideUse = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("< 1 week", "1-2 weeks", "> 2 weeks").forEach { option ->
                FilterChip(
                    selected = weeklyFreq == option,
                    onClick = { weeklyFreq = option },
                    label = { Text(option) },
                    modifier = Modifier.weight(1f).padding(horizontal = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        AppButton(text = "Continue", onClick = onContinue)
    }
}
