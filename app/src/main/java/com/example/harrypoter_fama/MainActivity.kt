package com.example.harrypoter_fama

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.harrypoter_fama.Utils.Companion.isNetAvailable
import com.example.harrypoter_fama.adapters.CharacterAdapter
import com.example.harrypoter_fama.api.ApiAdapter
import com.example.harrypoter_fama.databinding.ActivityMainBinding
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
        lateinit var apiResponse: Response<ArrayList<Character>>

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
                }
            else
                Toast.makeText(applicationContext, apiResponse.message(), Toast.LENGTH_LONG).show()
        }
    }

    private fun showCharacters(characters:ArrayList<Character>){
        val mLayoutManager = FlexboxLayoutManager(this)
        mLayoutManager.setFlexDirection(FlexDirection.ROW)
        mLayoutManager.setJustifyContent(JustifyContent.FLEX_START)
        mLayoutManager.flexWrap = FlexWrap.WRAP

        binding.vRecyclerCharacters.layoutManager = mLayoutManager
        binding.vRecyclerCharacters.adapter = CharacterAdapter(characters, R.layout.item_personaje)
    }
}