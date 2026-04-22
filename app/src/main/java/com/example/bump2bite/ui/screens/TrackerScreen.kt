package com.example.bump2bite.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bump2bite.ui.components.BottomNavigationBar
import com.example.bump2bite.ui.theme.TealPrimary
import com.example.bump2bite.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerScreen(viewModel: AppViewModel, onTabSelected: (String) -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Health Tracker", fontWeight = FontWeight.Bold) }
            )
        },
        bottomBar = { BottomNavigationBar(currentRoute = "tracker", onNavigate = onTabSelected) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(
                text = "Risk Trend",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Your oral health progress over time", color = Color.Gray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(32.dp))

            // Simple Chart Placeholder
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Box(modifier = Modifier.padding(20.dp)) {
                    LineChart()
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Monthly Summary",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            SummaryItem("Current Risk", "Moderate", Color(0xFFFBC02D))
            SummaryItem("Improvement", "+12%", Color(0xFF4CAF50))
            SummaryItem("Last Test", "2 days ago", Color.Gray)
        }
    }
}

@Composable
fun LineChart() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val points = listOf(0.8f, 0.6f, 0.7f, 0.4f, 0.45f) // Dummy data
        val width = size.width
        val height = size.height
        val spacing = width / (points.size - 1)

        val path = Path().apply {
            moveTo(0f, height * (1 - points[0]))
            for (i in 1 until points.size) {
                lineTo(i * spacing, height * (1 - points[i]))
            }
        }

        drawPath(
            path = path,
            color = TealPrimary,
            style = Stroke(width = 8f)
        )

        // Draw points
        for (i in points.indices) {
            drawCircle(
                color = TealPrimary,
                radius = 12f,
                center = Offset(i * spacing, height * (1 - points[i]))
            )
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray)
        Text(text = value, fontWeight = FontWeight.Bold, color = valueColor)
    }
}
