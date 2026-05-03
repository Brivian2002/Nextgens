package com.nextgens.app.presentation.screens.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeaderSection()
        }
        item {
            VpnButtonSection()
        }
        item {
            QuickTilesSection()
        }
        item {
            LiveSpeedGraphSection()
        }
        item {
            NetworkDetailsSection()
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "Nextgens",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A237E)
            )
            Text(
                "Securing your digital life",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Disconnected", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun VpnButtonSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(160.dp)
                .clickable { /* Toggle VPN */ },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Outlined.PowerSettingsNew,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                    tint = Color.Gray
                )
                Text(
                    "CONNECT",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun QuickTilesSection() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        QuickTile(Modifier.weight(1f), "Hotspot", Icons.Outlined.Wifi, Color(0xFFE3F2FD), Color(0xFF1E88E5))
        QuickTile(Modifier.weight(1f), "Mesh", Icons.Outlined.PhoneAndroid, Color(0xFFE8EAF6), Color(0xFF3F51B5))
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        QuickTile(Modifier.weight(1f), "Global Share", Icons.Outlined.Public, Color(0xFFE0F7FA), Color(0xFF00BCD4))
        QuickTile(Modifier.weight(1f), "4G Lite", Icons.Outlined.Bolt, Color(0xFFFFF8E1), Color(0xFFFFA000))
    }
}

@Composable
fun QuickTile(modifier: Modifier, label: String, icon: ImageVector, bgColor: Color, iconColor: Color) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(label, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun LiveSpeedGraphSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Live Network Speed", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                val path = Path().apply {
                    moveTo(0f, size.height * 0.8f)
                    cubicTo(size.width * 0.3f, size.height * 0.7f, size.width * 0.7f, size.height * 0.4f, size.width, size.height * 0.5f)
                }
                drawPath(path, Color(0xFF1A237E), style = Stroke(width = 2.dp.toPx()))
                // Draw shaded area
                val fillPath = Path().apply {
                    addPath(path)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(fillPath, Brush.verticalGradient(listOf(Color(0xFF1A237E).copy(alpha = 0.2f), Color.Transparent)))
            }
        }
    }
}

@Composable
fun NetworkDetailsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Wifi, contentDescription = null, tint = Color(0xFF1A237E))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Current Network", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text("Nextgens_Secure_Wifi", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Outlined.Refresh, contentDescription = null, tint = Color.Gray)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                NetworkDetailItem(Modifier.weight(1f), "IP Address", "192.168.1.15")
                NetworkDetailItem(Modifier.weight(1f), "Signal Strength", "Excellent")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E).copy(alpha = 0.05f))
            ) {
                Text("SCAN NEARBY NETWORKS", color = Color(0xFF1A237E), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun NetworkDetailItem(modifier: Modifier, label: String, value: String) {
    Column(modifier = modifier) {
        Text(label.uppercase(), style = MaterialTheme.typography.labelSmall, color = Color.Gray, letterSpacing = 1.sp)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}
