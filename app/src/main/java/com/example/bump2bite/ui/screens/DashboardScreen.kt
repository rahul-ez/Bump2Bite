package com.example.bump2bite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bump2bite.ui.components.BottomNavigationBar
import com.example.bump2bite.ui.components.SectionHeader
import com.example.bump2bite.ui.theme.PeachPrimary
import com.example.bump2bite.ui.theme.SoftPeach
import com.example.bump2bite.ui.theme.SoftTeal
import com.example.bump2bite.ui.theme.TealPrimary
import com.example.bump2bite.viewmodel.AppViewModel
import com.example.bump2bite.viewmodel.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: AppViewModel,
    onNavigateToTest: () -> Unit,
    onNavigateToTips: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogout: () -> Unit,
    onTabSelected: (String) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        if (viewModel.currentLanguage == Language.ENGLISH) "Bump2Bite" else "ಬಂಪ್2ಬೈಟ್",
                        fontWeight = FontWeight.Bold,
                        color = TealPrimary
                    ) 
                },
                navigationIcon = {
                    TextButton(onClick = { viewModel.toggleLanguage() }) {
                        Text(if (viewModel.currentLanguage == Language.ENGLISH) "KN" else "EN", color = TealPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = TealPrimary, modifier = Modifier.size(32.dp))
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text("View/Edit Profile") },
                            onClick = { 
                                showMenu = false
                                onNavigateToProfile() 
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = { 
                                showMenu = false
                                viewModel.logout()
                                onLogout()
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { BottomNavigationBar(currentRoute = "home", onNavigate = onTabSelected) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Risk Score Card
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)), // Light Yellow for Moderate
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Risk Score", fontWeight = FontWeight.Medium, color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Moderate Risk", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFBC02D))
                            Spacer(modifier = Modifier.weight(1f))
                            Text("45/100", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Tiles Grid
                Row(modifier = Modifier.fillMaxWidth()) {
                    DashboardTile(
                        title = "Take Assessment",
                        icon = Icons.Default.Quiz,
                        color = SoftTeal,
                        contentColor = TealPrimary,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToTest
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    DashboardTile(
                        title = "Counseling Video",
                        icon = Icons.Default.PlayCircle,
                        color = SoftPeach,
                        contentColor = PeachPrimary,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToTips
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader("Recommendations")
            }

            items(dashboardRecommendations) { recommendation ->
                RecommendationItem(recommendation)
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun DashboardTile(
    title: String,
    icon: ImageVector,
    color: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = modifier
            .height(120.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = contentColor, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = contentColor)
        }
    }
}

@Composable
fun RecommendationItem(recommendation: DashboardRecommendation) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TealPrimary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = recommendation.text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

data class DashboardRecommendation(val text: String)

val dashboardRecommendations = listOf(
    DashboardRecommendation("Brush your teeth twice a day with fluoridated toothpaste"),
    DashboardRecommendation("Avoid sugary snacks before bed"),
    DashboardRecommendation("Schedule your 2nd trimester dental checkup")
)
