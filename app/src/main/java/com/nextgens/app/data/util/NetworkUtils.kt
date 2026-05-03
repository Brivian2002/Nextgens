package com.nextgens.app.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Collections

object NetworkUtils {

    fun isVpnActive(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        } else {
            val networks = connectivityManager.allNetworks
            for (network in networks) {
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    return true
                }
            }
        }
        return false
    }

    fun getHotspotIpAddress(): String? {
        try {
            val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (networkInterface in interfaces) {
                if (networkInterface.name.contains("wlan") || networkInterface.name.contains("ap")) {
                    val addresses = Collections.list(networkInterface.inetAddresses)
                    for (address in addresses) {
                        if (!address.isLoopbackAddress && address is InetAddress) {
                            val hostAddress = address.hostAddress
                            if (hostAddress != null && hostAddress.indexOf(':') < 0) {
                                return hostAddress
                            }
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun isDeviceRooted(): Boolean {
        val buildTags = Build.TAGS
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true
        }
        try {
            val file = java.io.File("/system/app/Superuser.apk")
            if (file.exists()) return true
        } catch (e1: Exception) {
        }
        return canExecuteCommand("/system/xbin/which su") || 
               canExecuteCommand("/system/bin/which su") || 
               canExecuteCommand("which su")
    }

    private fun canExecuteCommand(command: String): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(command)
            val `in` = java.io.BufferedReader(java.io.InputStreamReader(process.inputStream))
            `in`.readLine() != null
        } catch (e: Exception) {
            false
        } finally {
            process?.destroy()
        }
    }
}
