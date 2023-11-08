package com.danilovfa.pexels.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danilovfa.pexels.data.local.model.PhotoEntity

@Database(
    entities = [PhotoEntity::class],
    version = PexelsDatabase.VERSION
)
abstract class PexelsDatabase: RoomDatabase() {
    abstract val dao: PexelsDao

    companion object {
        const val VERSION = 1
        const val NAME = "pexels_db"
    }
}