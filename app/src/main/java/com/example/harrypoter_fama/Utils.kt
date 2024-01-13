package com.example.harrypoter_fama

import android.widget.ImageView
import com.squareup.picasso.Picasso

class Utils {
    companion object {
        fun ImageView.fromUrl(url: String) {
            if (url.length > 0)
            Picasso
                .get()
                .load(url)
                .placeholder(context.resources.getDrawable(R.drawable.bat))
                .into(this)
        }
    }
}