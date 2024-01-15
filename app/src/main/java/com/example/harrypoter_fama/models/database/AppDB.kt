package com.example.harrypoter_fama.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.harrypoter_fama.models.database.dao.CharacterDao
import com.example.harrypoter_fama.models.database.entities.Character

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}