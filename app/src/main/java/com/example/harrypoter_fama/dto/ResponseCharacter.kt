package com.example.harrypoter_fama.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterResponse(
    @SerializedName("id") var id:String,
    @SerializedName("name") var name:String,
    @SerializedName("species") var species:String,
    @SerializedName("gender") var gender:String,
    @SerializedName("image") var image_path:String,
    @SerializedName("house") var house:String,
    @SerializedName("wand") var wand: CharacterWand,
    @SerializedName("actor") var actor:String,
    @SerializedName("dateOfBirth") var dateOfBirth:String?
):Parcelable{
    //representa yearOfBirth, como  yearOfBirth1 debido a que el nombre proporcionado ya viene en la api
    var yearOfBirth1: Int = 1999
    init {
        if (dateOfBirth != null && !dateOfBirth.isNullOrEmpty()) {
            yearOfBirth1 = dateOfBirth!!.split("-").get(2).toInt()
        }
    }
}

@Parcelize
data class CharacterWand(
    @SerializedName("wood") var wood:String?,
    @SerializedName("core") var core:String? = "Unknown",
    @SerializedName("length") var length:Float? = 0.0f
):Parcelable