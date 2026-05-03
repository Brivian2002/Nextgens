package com.nextgens.app.presentation.screens.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NetworkTool(val id: String, val name: String, val description: String, val icon: ImageVector)

val TOOLS_LIST = listOf(
    NetworkTool("adblock", "Ad-blocker", "Block intrusive ads globally", Icons.Outlined.Shield),
    NetworkTool("firewall", "Firewall", "Per-app network control", Icons.Outlined.Lock),
    NetworkTool("speedtest", "Speed Test", "Benchmark connection", Icons.Outlined.Bolt),
    NetworkTool("wifi", "Wi-Fi Analyzer", "Channel & signal scan", Icons.Outlined.Wifi),
    NetworkTool("packet", "Packet Capture", "Analyze raw traffic", Icons.Outlined.Analytics),
    NetworkTool("ssh", "SSH Tunnel", "Secure remote access", Icons.Outlined.Terminal),
    NetworkTool("dns", "DNS Analytics", "Detailed DNS lookup logs", Icons.Outlined.Search),
    NetworkTool("usage", "Data Usage", "Monitor app consumption", Icons.Outlined.PowerSettingsNew),
)

@Composable
fun ToolsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            "Tools",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search network tools...", color = Color.Gray, fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color(0xFF1A237E),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                focusedContainerColor = Color(0xFFF5F5F5)
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(TOOLS_LIST) { tool ->
                ToolCard(tool)
            }
        }
    }
}

@Composable
fun ToolCard(tool: NetworkTool) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF1A237E).copy(alpha = 0.05f), RoundedCornerShape(8.dp)),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Icon(tool.icon, contentDescription = null, tint = Color(0xFF1A237E), modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(tool.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text(
                tool.description,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                lineHeight = 14.sp
            )
        }
    }
}
