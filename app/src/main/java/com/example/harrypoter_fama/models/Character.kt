package com.example.harrypoter_fama.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Characters")
data class Character(
    @PrimaryKey
    val id: String,
    val name: String?,
    val species: String?,
    val gender: String?,
    val image_path: String?,
    val house: String?,
    val actor: String?,
    val dateOfBirth: String?
)