package com.nextgens.app.presentation.screens.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgens.app.data.util.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _vpnState = MutableStateFlow(VpnState.DISCONNECTED)
    val vpnState = _vpnState.asStateFlow()

    private val _isVpnActive = MutableStateFlow(false)
    val isVpnActive = _isVpnActive.asStateFlow()

    private val _hotspotIp = MutableStateFlow<String?>(null)
    val hotspotIp = _hotspotIp.asStateFlow()

    init {
        monitorNetwork()
    }

    private fun monitorNetwork() {
        viewModelScope.launch {
            while (true) {
                _isVpnActive.value = NetworkUtils.isVpnActive(context)
                _hotspotIp.value = NetworkUtils.getHotspotIpAddress()
                delay(2000)
            }
        }
    }

    fun toggleVpn() {
        if (_vpnState.value == VpnState.DISCONNECTED) {
            _vpnState.value = VpnState.CONNECTING
            viewModelScope.launch {
                delay(1500)
                _vpnState.value = VpnState.CONNECTED
            }
        } else {
            _vpnState.value = VpnState.DISCONNECTED
        }
    }
}

enum class VpnState {
    DISCONNECTED, CONNECTING, CONNECTED
}
