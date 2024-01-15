package com.example.harrypoter_fama

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import com.example.harrypoter_fama.models.api.dto.CharacterResponse
import com.example.harrypoter_fama.models.api.dto.CharacterWand
import com.example.harrypoter_fama.models.database.entities.Character
import com.example.harrypoter_fama.models.database.entities.Wand
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL


class Utils {
    companion object {
        fun ImageView.fromUrl(url: String) {
            if (url.isNotEmpty() && this.context.isNetAvailable())
                Picasso.get().load(url)
                    .placeholder(R.drawable.harrybird)
                    .error(R.drawable.harrybird)
                    .into(this)
        }

        fun ImageView.toByteArray():ByteArray{
            val bitmap = (this.drawable as BitmapDrawable).bitmap
            val bs = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs)
            return bs.toByteArray()
        }

        fun ByteArray.toBitmap(): Bitmap {
            return BitmapFactory.decodeByteArray(this,0,size)
        }

        fun Context.isNetAvailable(): Boolean {
            var result = false
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
            return result
        }

        fun List<Character>.toCharacterDTO(): List<CharacterResponse>{
            val mapped = this.map {
                    CharacterResponse(
                        id = it.id,
                        name = it.name ?: "",
                        species = it.species ?: "",
                        gender = it.gender ?: "",
                        image_path = it.image_path ?: "",
                        house = it.house ?: "",
                        actor = it.actor ?: "",
                        dateOfBirth = it.dateOfBirth,
                        wand = CharacterWand(
                            wood = it.wand.wood ?: "Unknowm",
                            core = it.wand.core ?: "Unknowm",
                            length = it.wand.length ?: 0.0f
                        )
                    )
            }
            return mapped
        }

        fun List<CharacterResponse>.toCharacterEntity(): List<Character>{
            val mapped = this.map {
                Character(
                    id = it.id,
                    name = it.name,
                    species = it.species,
                    gender = it.gender,
                    image_path = it.image_path,
                    house = it.house,
                    actor = it.actor,
                    dateOfBirth = it.dateOfBirth,
                    wand = Wand(
                        wood = it.wand.wood,
                        core = it.wand.core,
                        length = it.wand.length
                    )
                )
            }
            return mapped
        }
    }
}