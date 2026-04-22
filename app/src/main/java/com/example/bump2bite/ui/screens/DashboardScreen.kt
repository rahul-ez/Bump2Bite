package com.example.bump2bite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bump2bite.ui.components.AppButton
import com.example.bump2bite.ui.components.BottomNavigationBar
import com.example.bump2bite.ui.components.RiskIndicator
import com.example.bump2bite.ui.components.SectionHeader
import com.example.bump2bite.ui.theme.PeachPrimary
import com.example.bump2bite.ui.theme.SoftTeal
import com.example.bump2bite.ui.theme.TealPrimary

@Composable
fun DashboardScreen(onNavigateToTips: () -> Unit) {
    Scaffold(
        bottomBar = { BottomNavigationBar(currentRoute = "home") }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Hello, Sarah! 👋", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text(text = "Today, May 18", color = Color.Gray)
                    }
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(SoftTeal),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = TealPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                RiskIndicator(score = 45, label = "Mild", color = TealPrimary)

                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Good job! Your overall caries risk is mild. Keep up with good oral care habits!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                AppButton(
                    text = "Watch Counseling Video",
                    onClick = { /* Handle video */ },
                    containerColor = TealPrimary.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                SectionHeader("Recommendations for Today")
            }

            items(recommendations) { recommendation ->
                RecommendationItem(recommendation)
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                AppButton(text = "View All Tips", onClick = onNavigateToTips)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun RecommendationItem(recommendation: Recommendation) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = recommendation.backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(recommendation.icon, contentDescription = null, tint = recommendation.iconColor)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = recommendation.text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

data class Recommendation(
    val text: String,
    val icon: ImageVector,
    val iconColor: Color,
    val backgroundColor: Color
)

val recommendations = listOf(
    Recommendation(
        "Brush your teeth twice a day with fluoridated toothpaste",
        Icons.Default.CheckCircle,
        TealPrimary,
        SoftTeal
    ),
    Recommendation(
        "Avoid sugary snacks and soft drinks before bed",
        Icons.Default.Warning,
        PeachPrimary,
        Color(0xFFFFF3E0)
    )
)
