package com.example.harrypoter_fama.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.harrypoter_fama.models.database.AppDatabase
import com.example.harrypoter_fama.R
import com.example.harrypoter_fama.Utils.Companion.isNetAvailable
import com.example.harrypoter_fama.Utils.Companion.toCharacterDTO
import com.example.harrypoter_fama.Utils.Companion.toCharacterEntity
import com.example.harrypoter_fama.views.adapters.CharacterAdapter
import com.example.harrypoter_fama.models.api.ApiAdapter
import com.example.harrypoter_fama.databinding.ActivityMainBinding
import com.example.harrypoter_fama.models.api.dto.CharacterResponse
import com.example.harrypoter_fama.models.database.entities.Character
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
        installSplashScreen()

        setContentView(binding.root)

        if(isNetAvailable())
            getCharactersFromApi()//obtener de internet y guardar o actualizar en room
        else
        {
            //obtener de la bd local
            lifecycleScope.launch {
                var characters = getAllCharacters()
                showCharacters(characters.toCharacterDTO())
            }
        }
    }

    private fun getCharactersFromApi(){
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
                }
            else
                Toast.makeText(applicationContext, apiResponse.message(), Toast.LENGTH_LONG).show()
        }
    }

    private fun showCharacters(characterResponses:List<CharacterResponse>){
        val mLayoutManager = FlexboxLayoutManager(this)
        mLayoutManager.setFlexDirection(FlexDirection.ROW)
        mLayoutManager.setJustifyContent(JustifyContent.FLEX_START)
        mLayoutManager.flexWrap = FlexWrap.WRAP

        binding.vRecyclerCharacters.layoutManager = mLayoutManager
        binding.vRecyclerCharacters.adapter = CharacterAdapter(characterResponses,
            R.layout.item_personaje
        )
    }

    private suspend fun saveAllCharacters(characters: ArrayList<CharacterResponse>) {
        val dataToInsert = characters.toCharacterEntity()
        db.characterDao.inserAll(dataToInsert)
    }

    private suspend fun getAllCharacters():List<Character>{
        return db.characterDao.getAll()
    }
}