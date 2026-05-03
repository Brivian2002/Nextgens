package com.nextgens.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nextgens.app.data.local.entity.VpnProfile

@Database(entities = [VpnProfile::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    // DAO methods would go here
}
