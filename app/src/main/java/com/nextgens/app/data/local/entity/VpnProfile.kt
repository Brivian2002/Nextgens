package com.nextgens.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vpn_profiles")
data class VpnProfile(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val config: String
)
