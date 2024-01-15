package com.example.harrypoter_fama.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wand(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "wood") val wood: String?="Unknown",
    @ColumnInfo(name = "core") val core: String? = "Unknown",
    @ColumnInfo(name = "length") val length: Float? = 0.0f
)