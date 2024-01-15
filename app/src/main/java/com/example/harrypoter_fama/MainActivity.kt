package com.example.harrypoter_fama

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.harrypoter_fama.Utils.Companion.isNetAvailable
import com.example.harrypoter_fama.adapters.CharacterAdapter
import com.example.harrypoter_fama.api.ApiAdapter
import com.example.harrypoter_fama.databinding.ActivityMainBinding
import com.example.harrypoter_fama.dto.CharacterResponse
import com.example.harrypoter_fama.models.Character
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app_database.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(isNetAvailable())
            Toast.makeText(this, "hay red", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Sin red", Toast.LENGTH_SHORT).show()

        getCharacters()
    }

    private fun getCharacters(){
        lateinit var apiResponse: Response<ArrayList<CharacterResponse>>

        lifecycleScope.launch {
            withContext(Dispatchers.IO)
            {
                try {
                    apiResponse = ApiAdapter.apiClient.getCharacters()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main){
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            if(apiResponse.isSuccessful && apiResponse.code() == 200 )
                apiResponse.body()?.let {
                    showCharacters(it)
                    saveAllCharacters(it)
                    var datos = getAllCharacters()
                    var first = datos.get(0)
                }
            else
                Toast.makeText(applicationContext, apiResponse.message(), Toast.LENGTH_LONG).show()
        }
    }

    private fun showCharacters(characterResponses:ArrayList<CharacterResponse>){
        val mLayoutManager = FlexboxLayoutManager(this)
        mLayoutManager.setFlexDirection(FlexDirection.ROW)
        mLayoutManager.setJustifyContent(JustifyContent.FLEX_START)
        mLayoutManager.flexWrap = FlexWrap.WRAP

        binding.vRecyclerCharacters.layoutManager = mLayoutManager
        binding.vRecyclerCharacters.adapter = CharacterAdapter(characterResponses, R.layout.item_personaje)
    }

    private suspend fun saveAllCharacters(characters: ArrayList<CharacterResponse>) {
        val dataToInsert = characters.map {
            Character(
                id = it.id,
               name = it.name,
                species = it.species,
                gender = it.gender,
                image_path = it.image_path,
                house = it.house,
                actor = it.actor,
                dateOfBirth = it.dateOfBirth
            )
        }

        db.characterDao.inserAll(dataToInsert)
    }

    private suspend fun getAllCharacters():List<Character>{
        return db.characterDao.getAll()
    }
}