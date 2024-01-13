package com.example.harrypoter_fama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypoter_fama.adapters.CharacterAdapter
import com.example.harrypoter_fama.api.ApiAdapter
import com.example.harrypoter_fama.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import com.example.harrypoter_fama.models.Character


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCharacters();
    }

    private fun getCharacters(){
        lateinit var apiResponse: Response<ArrayList<Character>>

        lifecycleScope.launch {
            withContext(Dispatchers.IO)
            {
                try {
                    apiResponse = ApiAdapter.apiClient.getCharacters();
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
        val mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false )
        binding.vRecyclerCharacters.layoutManager = mLayoutManager
        binding.vRecyclerCharacters.adapter = CharacterAdapter(characters, R.layout.item_personaje)
    }
}