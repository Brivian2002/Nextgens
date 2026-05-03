package com.nextgens.app.presentation.screens.code

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CodeScreen(viewModel: CodeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val script by viewModel.script.collectAsState()
    val consoleOutput by viewModel.consoleOutput.collectAsState()
    val isExecuting by viewModel.isExecuting.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            "Code Executor",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E)
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        // Editor Section
        Card(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color(0xFF252525)).padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Terminal, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("EDITOR (SHELL)", color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Row {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Red.copy(0.5f)))
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Yellow.copy(0.5f)))
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Green.copy(0.5f)))
                    }
                }
                TextField(
                    value = script,
                    onValueChange = { viewModel.updateScript(it) },
                    modifier = Modifier.fillMaxSize(),
                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = Color(0xFF00BCD4)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFF00BCD4)
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Templates
        val templates = listOf("Flush DNS", "Reset Network", "Kill Apps", "Speed Test")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(templates) { template ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF5F5F5))
                        .clickable { 
                            when(template) {
                                "Flush DNS" -> viewModel.updateScript("ndc resolver flushdefaultif\nndc resolver flushif wlan0")
                                "Reset Network" -> viewModel.updateScript("svc wifi disable\nsvc wifi enable")
                                "Kill Apps" -> viewModel.updateScript("am kill-all")
                                "Speed Test" -> viewModel.updateScript("ping -c 4 8.8.8.8")
                            }
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(template, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { viewModel.executeScript() },
                modifier = Modifier.weight(1f).height(48.dp),
                enabled = !isExecuting,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))
            ) {
                if (isExecuting) {
                    androidx.compose.material3.CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                } else {
                    Icon(Icons.Outlined.PlayArrow, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isExecuting) "EXECUTING..." else "RUN SCRIPT", fontWeight = FontWeight.Bold)
            }
            IconButton(
                onClick = { viewModel.clearConsole() },
                modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFF5F5F5))
            ) {
                Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.Gray)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Console Output
        Card(
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.95f))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Code, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("CONSOLE OUTPUT", color = Color.Gray, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(consoleOutput) { line ->
                        Text(
                            line,
                            color = if (line.startsWith("$") || line.startsWith(">")) Color.Green.copy(0.7f) else Color.White.copy(0.8f),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}
