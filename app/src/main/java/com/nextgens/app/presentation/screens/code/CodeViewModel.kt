package com.nextgens.app.presentation.screens.code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgens.app.data.util.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class CodeViewModel @Inject constructor() : ViewModel() {

    private val _script = MutableStateFlow("echo \"Starting diagnostics...\"\nip addr show\ngetprop ro.product.model")
    val script = _script.asStateFlow()

    private val _consoleOutput = MutableStateFlow<List<String>>(listOf("> Idle. Ready to run scripts."))
    val consoleOutput = _consoleOutput.asStateFlow()

    private val _isExecuting = MutableStateFlow(false)
    val isExecuting = _isExecuting.asStateFlow()

    fun updateScript(newScript: String) {
        _script.value = newScript
    }

    fun clearConsole() {
        _consoleOutput.value = emptyList()
    }

    fun executeScript() {
        if (_isExecuting.value) return
        
        viewModelScope.launch {
            _isExecuting.value = true
            _consoleOutput.value = _consoleOutput.value + "> Executing script..."
            
            val commands = _script.value.split("\n").filter { it.isNotBlank() }
            
            for (cmd in commands) {
                _consoleOutput.value = _consoleOutput.value + "$ $cmd"
                val result = runCommand(cmd)
                _consoleOutput.value = _consoleOutput.value + result
            }
            
            _consoleOutput.value = _consoleOutput.value + "> Done."
            _isExecuting.value = false
        }
    }

    private fun runCommand(command: String): List<String> {
        val output = mutableListOf<String>()
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec(command)
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.add(line!!)
                if (output.size > 20) break // Limit output
            }
            val errorReader = BufferedReader(InputStreamReader(process.errorStream))
            while (errorReader.readLine().also { line = it } != null) {
                output.add("Error: $line")
            }
        } catch (e: Exception) {
            output.add("Exception: ${e.message}")
        } finally {
            process?.destroy()
        }
        return if (output.isEmpty()) listOf("(No output)") else output
    }
}
