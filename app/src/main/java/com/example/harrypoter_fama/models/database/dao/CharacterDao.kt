package com.example.harrypoter_fama.models.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.harrypoter_fama.models.database.entities.Character

@Dao
interface CharacterDao {
    @Upsert
    suspend fun inserAll(characters:List<Character>)
    @Query("select * from Characters")
    suspend fun getAll():List<Character>
}