package com.example.bump2bite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bump2bite.ui.components.AppButton
import com.example.bump2bite.ui.theme.TealPrimary
import com.example.bump2bite.viewmodel.AppViewModel

data class Question(
    val text: String,
    val options: List<String>
)

val assessmentQuestions = listOf(
    Question("How often do you brush your teeth?", listOf("Twice a day", "Once a day", "Occasionally")),
    Question("Do you use fluoridated toothpaste?", listOf("Always", "Sometimes", "Never")),
    Question("How often do you consume sugary snacks?", listOf("Rarely", "Once a day", "Multiple times a day")),
    Question("Do you experience bleeding gums?", listOf("Never", "Sometimes", "Frequently")),
    Question("When was your last dental visit?", listOf("Within 6 months", "Last year", "More than 2 years")),
    Question("Do you rinse after every meal?", listOf("Always", "Sometimes", "Never")),
    Question("Do you use dental floss?", listOf("Daily", "Weekly", "Never")),
    Question("Are you currently experiencing tooth pain?", listOf("No", "Mild sensitivity", "Yes, severe")),
    Question("How much water do you drink daily?", listOf("8+ glasses", "4-6 glasses", "Less than 3")),
    Question("Do you have any visible cavities?", listOf("None", "Maybe one", "Yes, multiple"))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(viewModel: AppViewModel, onTestComplete: () -> Unit, onBack: () -> Unit) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswers by remember { mutableStateOf(MutableList(assessmentQuestions.size) { -1 }) }
    var showResult by remember { mutableStateOf(false) }

    if (showResult) {
        TestResultScreen(onViewTips = onTestComplete)
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Oral Health Assessment", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { 
                            if (currentQuestionIndex > 0) {
                                currentQuestionIndex-- 
                            } else {
                                onBack()
                            }
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp)
            ) {
                // Progress Bar
                LinearProgressIndicator(
                    progress = (currentQuestionIndex + 1).toFloat() / assessmentQuestions.size,
                    modifier = Modifier.fillMaxWidth().height(8.dp),
                    color = TealPrimary,
                    trackColor = Color.LightGray
                )
                
                Spacer(modifier = Modifier.height(32.dp))

                val question = assessmentQuestions[currentQuestionIndex]
                
                Text(
                    text = "Question ${currentQuestionIndex + 1} of ${assessmentQuestions.size}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = question.text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                question.options.forEachIndexed { index, option ->
                    val isSelected = selectedAnswers[currentQuestionIndex] == index
                    OutlinedCard(
                        onClick = {
                            val newAnswers = selectedAnswers.toMutableList()
                            newAnswers[currentQuestionIndex] = index
                            selectedAnswers = newAnswers
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = if (isSelected) TealPrimary.copy(alpha = 0.1f) else Color.Transparent,
                            contentColor = if (isSelected) TealPrimary else Color.Black
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, 
                            if (isSelected) TealPrimary else Color.LightGray
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(selectedColor = TealPrimary)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = option, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                AppButton(
                    text = if (currentQuestionIndex == assessmentQuestions.size - 1) "Submit" else "Next",
                    onClick = {
                        if (currentQuestionIndex < assessmentQuestions.size - 1) {
                            currentQuestionIndex++
                        } else {
                            // Calculate score logic here
                            viewModel.riskScore = 35 // Just a dummy update
                            showResult = true
                        }
                    },
                    enabled = selectedAnswers[currentQuestionIndex] != -1
                )
            }
        }
    }
}

@Composable
fun TestResultScreen(onViewTips: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = TealPrimary,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text("Assessment Complete!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Based on your answers, your caries risk level is:",
            color = Color.Gray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            color = Color(0xFFFFF9C4),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ) {
            Text(
                "Moderate Risk",
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFBC02D)
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        AppButton(text = "Watch Counselling Video", onClick = onViewTips)
    }
}
