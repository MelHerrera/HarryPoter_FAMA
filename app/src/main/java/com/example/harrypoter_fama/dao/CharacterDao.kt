package com.example.harrypoter_fama.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.harrypoter_fama.models.Character

@Dao
interface CharacterDao {
    @Insert
    suspend fun inserAll(characters:List<Character>)
    @Query("select * from Characters")
    suspend fun getAll():List<Character>
}