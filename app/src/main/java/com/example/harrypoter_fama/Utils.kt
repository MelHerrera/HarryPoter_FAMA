package com.example.harrypoter_fama

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


class Utils {
    companion object {
        fun ImageView.fromUrl(url: String) {
            if (url.length > 0){
                Picasso.get().load(url)
                    .placeholder(context.resources.getDrawable(R.drawable.bat))
                    .into(this)
            }
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
    }
}