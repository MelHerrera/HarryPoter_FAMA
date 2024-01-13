package com.example.harrypoter_fama.models

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("name") var name:String,
    @SerializedName("species") var species:String,
    @SerializedName("gender") var gender:String,
    @SerializedName("image") var image_path:String
)