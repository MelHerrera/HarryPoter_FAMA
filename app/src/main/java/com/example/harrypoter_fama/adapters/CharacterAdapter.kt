package com.example.harrypoter_fama.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypoter_fama.R
import com.example.harrypoter_fama.Utils.Companion.fromUrl
import com.example.harrypoter_fama.models.Character

class CharacterAdapter(private val characters: ArrayList<Character>, private val itemViewReference:Int)
    : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(itemViewReference, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val moviePoster: ImageView = itemView.findViewById(R.id.vPoster)

        fun bindItem(character: Character){
            moviePoster.fromUrl(character.image_path)
        }
    }
}