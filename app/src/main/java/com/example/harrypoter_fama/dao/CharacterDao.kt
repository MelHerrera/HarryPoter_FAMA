package com.example.harrypoter_fama.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.harrypoter_fama.models.Character

@Dao
interface CharacterDao {
    @Upsert
    suspend fun inserAll(characters:List<Character>)
    @Query("select * from Characters")
    suspend fun getAll():List<Character>
}