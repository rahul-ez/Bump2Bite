package com.example.bump2bite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.bump2bite.ui.theme.PeachPrimary
import com.example.bump2bite.ui.theme.SoftPeach
import com.example.bump2bite.ui.theme.SoftTeal
import com.example.bump2bite.ui.theme.TealPrimary
import com.example.bump2bite.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostpartumScreen(viewModel: AppViewModel, onLogout: () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Bump2Bite", fontWeight = FontWeight.Bold, color = TealPrimary) },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = TealPrimary, modifier = Modifier.size(32.dp))
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
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
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Postpartum Care",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TealPrimary
                )
                Text(
                    text = "Essential guidance for you and your baby",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Video Section
                Text("Counseling Video", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Black)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.PlayCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Play Video", color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
                Text("Key Oral Care Tips", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(postpartumTips) { tip ->
                PostpartumTipItem(tip)
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text("Frequently Asked Questions", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(postpartumFaqs) { faq ->
                PostpartumFaqItem(faq)
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun PostpartumTipItem(tip: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SoftTeal.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TealPrimary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = tip, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun PostpartumFaqItem(faq: Pair<String, String>) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = faq.first, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Icon(if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = null)
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = faq.second, style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
            }
        }
    }
}

val postpartumTips = listOf(
    "Wipe your baby's gums with a clean, damp cloth twice a day.",
    "Avoid putting your baby to bed with a bottle of milk or juice.",
    "Schedule your baby's first dental visit by their first birthday.",
    "Avoid sharing spoons or cleaning pacifiers with your own mouth."
)

val postpartumFaqs = listOf(
    "When should I start brushing my baby's teeth?" to "Start as soon as the first tooth appears using a small, soft-bristled brush.",
    "Is it normal for my baby to drool a lot?" to "Yes, drooling is often a sign of teething, which usually starts around 6 months.",
    "What should I do if my baby has a toothache?" to "Check for any trapped food, rinse with warm water, and consult a pediatric dentist.",
    "Can breastfeeding cause cavities?" to "While breast milk is natural, prolonged on-demand feeding, especially at night, can increase risk if hygiene is not maintained."
)
