package com.example.harrypoter_fama.views.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypoter_fama.views.CharacterDetailsActivity
import com.example.harrypoter_fama.R
import com.example.harrypoter_fama.Utils.Companion.fromUrl
import com.example.harrypoter_fama.Utils.Companion.toByteArray
import com.example.harrypoter_fama.models.api.dto.CharacterResponse
import java.lang.reflect.Array
import java.util.Locale


class CharacterAdapter(private var characterResponses: List<CharacterResponse>, private val itemViewReference:Int)
    : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(), Filterable {
    var listFull: List<CharacterResponse> = characterResponses

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(itemViewReference, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(characterResponses[position])
    }

    override fun getItemCount(): Int {
        return characterResponses.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val personajePoster: ImageView = itemView.findViewById(R.id.vPoster)
        private val personajeNombres: TextView = itemView.findViewById(R.id.vName)
        private val personajeSpeciesAndGender: TextView =
            itemView.findViewById(R.id.vSpeciesAndGender)

        fun bindItem(characterResponse: CharacterResponse) {
            personajePoster.fromUrl(characterResponse.image_path)
            personajeNombres.text = characterResponse.name
            personajeSpeciesAndGender.text =
                "${characterResponse.species} - ${characterResponse.gender}"

            itemView.setOnClickListener {
                val mIntent = Intent(itemView.context, CharacterDetailsActivity::class.java)
                val dataToSend = Bundle()
                dataToSend.putParcelable("currentCharacter", characterResponse)
                dataToSend.putByteArray("imagePersonaje", personajePoster.toByteArray())

                mIntent.putExtras(dataToSend)
                itemView.context.startActivity(mIntent)
            }
        }
    }

    override fun getFilter(): Filter {
        return listamenufilter
    }

    private val listamenufilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList: MutableList<CharacterResponse> = ArrayList()
            var busqueda = constraint.toString().toUpperCase(Locale.ROOT)

            if (constraint.isNullOrEmpty())
                filteredList = listFull.toMutableList()
            else {
                for (item in listFull) {
                    if (item.name.uppercase().contains(busqueda)
                        || item.species.uppercase().contains(busqueda)
                        || item.actor.uppercase().contains(busqueda))
                        filteredList.add(item)
                }
            }

            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            characterResponses = emptyList()
            characterResponses = results?.values as List<CharacterResponse>
            notifyDataSetChanged()
        }
    }
}