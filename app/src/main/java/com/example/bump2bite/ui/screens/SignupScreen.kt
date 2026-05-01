package com.example.bump2bite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.clickable
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
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(viewModel: AppViewModel, onSignupSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var expectedDeliveryDate by remember { mutableStateOf("") }
    var isDelivered by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf(Language.ENGLISH) }
    
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    
    val authError = viewModel.authError
    var expandedLang by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        expectedDeliveryDate = sdf.format(Date(it))
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

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

        AppTextField(value = email, onValueChange = { email = it }, label = "Email")
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(value = password, onValueChange = { password = it }, label = "Password", isPassword = true)
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(value = name, onValueChange = { name = it }, label = "Full Name")
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(value = age, onValueChange = { age = it }, label = "Age")
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(value = city, onValueChange = { city = it }, label = "City/Town")
        
        Spacer(modifier = Modifier.height(16.dp))

        Text("Have you delivered?", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            SegmentedButton(
                selected = isDelivered,
                onClick = { isDelivered = true },
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                colors = SegmentedButtonDefaults.colors(activeContainerColor = TealPrimary.copy(alpha = 0.1f), activeBorderColor = TealPrimary, activeContentColor = TealPrimary)
            ) {
                Text("Yes")
            }
            SegmentedButton(
                selected = !isDelivered,
                onClick = { isDelivered = false },
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                colors = SegmentedButtonDefaults.colors(activeContainerColor = TealPrimary.copy(alpha = 0.1f), activeBorderColor = TealPrimary, activeContentColor = TealPrimary)
            ) {
                Text("No")
            }
        }

        if (!isDelivered) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = expectedDeliveryDate,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Expected Delivery Date") },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Select Date")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TealPrimary,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
                // Transparent overlay to catch clicks on the whole field
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Transparent)
                        .clickable { showDatePicker = true }
                )
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

        authError?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        AppButton(text = "Sign Up", onClick = {
            viewModel.signup(email, password, UserProfile(name, age, expectedDeliveryDate, isDelivered, city, language), onSignupSuccess)
        })
    }
}
