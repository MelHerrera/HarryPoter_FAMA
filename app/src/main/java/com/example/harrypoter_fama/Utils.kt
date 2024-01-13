package com.example.harrypoter_fama

import android.widget.ImageView
import com.squareup.picasso.Picasso

class Utils {
    companion object {
        fun ImageView.fromUrl(url: String) {
            Picasso.get().load(url).into(this)
        }
    }
}