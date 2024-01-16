package com.example.harrypoter_fama.views

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.harrypoter_fama.models.database.AppDatabase
import com.example.harrypoter_fama.R
import com.example.harrypoter_fama.Utils.Companion.isNetAvailable
import com.example.harrypoter_fama.Utils.Companion.toCharacterDTO
import com.example.harrypoter_fama.views.adapters.CharacterAdapter
import com.example.harrypoter_fama.databinding.ActivityMainBinding
import com.example.harrypoter_fama.models.api.dto.CharacterResponse
import com.example.harrypoter_fama.presenters.MainActivityPresenter
import com.example.harrypoter_fama.presenters.contracts.MainActivityContract
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity(), MainActivityContract.View {
    lateinit var binding: ActivityMainBinding
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app_database.db"
        ).build()
    }
    lateinit var presenter:MainActivityContract.Presenter
    lateinit var characterAdapter:CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        presenter = MainActivityPresenter(this)
        setSupportActionBar(binding.toolbarMain)

        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                if (isNetAndInternetAvailable())
                    presenter.getAllCharactersFromApi(db)
                else {
                    var characters = presenter.getAllCharactersFromDb(db)
                    showCharacters(characters.toCharacterDTO())
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)

        var menuSearch = menu?.findItem(R.id.menu_search)
        var viewSearch = menuSearch?.actionView as SearchView
        viewSearch.queryHint = "Nombre, especie, actor"

        viewSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                characterAdapter.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override suspend fun showCharacters(characterResponses: List<CharacterResponse>) {
        withContext(Dispatchers.Main){
            if (characterResponses.size <= 0){
                binding.vNoCharacters.visibility = View.VISIBLE
                binding.vtxtNoCharacters.visibility = View.VISIBLE
                binding.vRecyclerCharacters.visibility = View.GONE
            }
            else{
                binding.vNoCharacters.visibility = View.GONE
                binding.vtxtNoCharacters.visibility = View.GONE
                binding.vRecyclerCharacters.visibility = View.VISIBLE
            }

            val mLayoutManager = FlexboxLayoutManager(applicationContext)
            mLayoutManager.setFlexDirection(FlexDirection.ROW)
            mLayoutManager.setJustifyContent(JustifyContent.FLEX_START)
            mLayoutManager.flexWrap = FlexWrap.WRAP

            //guardar la instancia del adapter para que se encargue de filtrar
            characterAdapter = CharacterAdapter(characterResponses, R.layout.item_personaje)

            binding.vRecyclerCharacters.layoutManager = mLayoutManager
            binding.vRecyclerCharacters.adapter = characterAdapter
        }
    }

    override fun onGetCharacterError(error:Exception) {
        Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
    }

    override fun onResponseError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    fun isNetAndInternetAvailable():Boolean{
        return try {
            val mUrl= URL("https://www.google.com")
            val httpReques = mUrl.openConnection() as HttpURLConnection
            httpReques.connectTimeout=3000
            httpReques.connect()
            httpReques.responseCode== HttpURLConnection.HTTP_OK && isNetAvailable()
        }catch (e:Exception){
            false
        }
    }
}