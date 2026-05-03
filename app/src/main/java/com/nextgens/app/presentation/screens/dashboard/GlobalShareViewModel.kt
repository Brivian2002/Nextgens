package com.nextgens.app.presentation.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GlobalShareViewModel @Inject constructor() : ViewModel() {

    private val _isSharing = MutableStateFlow(false)
    val isSharing = _isSharing.asStateFlow()

    private val _shareCode = MutableStateFlow<String?>(null)
    val shareCode = _shareCode.asStateFlow()

    private val _status = MutableStateFlow("Ready to share")
    val status = _status.asStateFlow()

    fun startSharing() {
        viewModelScope.launch {
            _isSharing.value = true
            _status.value = "Generating secure keys..."
            delay(1000)
            _shareCode.value = (100000..999999).random().toString()
            _status.value = "Waiting for peer..."
        }
    }

    fun stopSharing() {
        _isSharing.value = false
        _shareCode.value = null
        _status.value = "Ready to share"
    }
}
