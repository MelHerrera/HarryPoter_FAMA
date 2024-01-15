package com.example.harrypoter_fama

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.harrypoter_fama.dao.CharacterDao
import com.example.harrypoter_fama.models.Character

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}