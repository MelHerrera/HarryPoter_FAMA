package com.example.harrypoter_fama.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypoter_fama.CharacterDetailsActivity
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
        private val personajePoster: ImageView = itemView.findViewById(R.id.vPoster)
        private val personajeNombres:TextView = itemView.findViewById(R.id.vName)
        private val personajeSpeciesAndGender:TextView = itemView.findViewById(R.id.vSpeciesAndGender)

        fun bindItem(character: Character){
            personajePoster.fromUrl(character.image_path)
            personajeNombres.text = character.name
            personajeSpeciesAndGender.text = "${character.species} - ${character.gender}"

            itemView.setOnClickListener {
                val myNewActivity=Intent(itemView.context,CharacterDetailsActivity::class.java)
                itemView.context.startActivity(myNewActivity)
            }
        }
    }
}