package com.nextgens.app.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E)
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        SettingSection("Connectivity") {
            SettingItem("VPN Profiles", Icons.Outlined.Shield, "3 profiles active")
            ItemDivider()
            SettingItem("Kill Switch", Icons.Outlined.Lock, isToggle = true, initialValue = true)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        SettingSection("Features") {
            SettingItem("Hotspot Sharing", Icons.Outlined.Wifi, isToggle = true)
            ItemDivider()
            SettingItem("Data Optimization", Icons.Outlined.Wifi, "Standard")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        SettingSection("About") {
            SettingItem("About Nextgens", Icons.Outlined.Info, "v1.0.0")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "DESIGNED BY NEXTGENS GLOBAL SECURITY",
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun SettingSection(title: String, content: @Composable ColumnLayoutScope.() -> Unit) {
    Column {
        Text(
            title.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Black,
            color = Color.Gray,
            letterSpacing = 2.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column {
                val scope = ColumnLayoutScopeImpl(this)
                scope.content()
            }
        }
    }
}

@Composable
fun SettingItem(
    label: String,
    icon: ImageVector,
    detail: String? = null,
    isToggle: Boolean = false,
    initialValue: Boolean = false
) {
    var checked by remember { mutableStateOf(initialValue) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFF1A237E).copy(alpha = 0.05f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF1A237E), modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.weight(1f))
        if (isToggle) {
            Switch(
                checked = checked,
                onCheckedChange = { checked = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF1A237E)
                )
            )
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (detail != null) {
                    Text(detail, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
            }
        }
    }
}

interface ColumnLayoutScope {
    @Composable
    fun ItemDivider()
}

class ColumnLayoutScopeImpl(private val columnScope: ColumnScope) : ColumnLayoutScope {
    @Composable
    override fun ItemDivider() {
        Divider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 0.5.dp,
            color = Color.LightGray.copy(alpha = 0.3f)
        )
    }
}
