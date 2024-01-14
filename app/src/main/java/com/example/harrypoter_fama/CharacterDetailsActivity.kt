package com.example.harrypoter_fama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harrypoter_fama.Utils.Companion.toBitmap
import com.example.harrypoter_fama.databinding.ActivityCharacterDetailsBinding
import com.example.harrypoter_fama.models.Character

class CharacterDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCharacterDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val character = intent.getParcelableExtra<Character>("currentCharacter")
        val characterImage = intent.getByteArrayExtra("imagePersonaje")

        binding.vImage.setImageBitmap(characterImage?.toBitmap())
        binding.vName.text = character?.name
        binding.vHouse.text = character?.house
        binding.vActor.text = character?.actor?.trim()
        binding.vSpecieGender.text = "${character?.species} - ${character?.gender}"
        binding.vBornAge.text = "Nac: ${character?.dateOfBirth} (${character?.yearOfBirth1})"
        binding.vTextMaderaValue.text = character?.wand?.wood
        binding.vTextNucleoValue.text = character?.wand?.core
        binding.vTextLongitudValue.text = character?.wand?.length?.toString()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}