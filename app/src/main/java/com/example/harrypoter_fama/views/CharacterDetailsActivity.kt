package com.example.harrypoter_fama.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harrypoter_fama.Utils.Companion.toBitmap
import com.example.harrypoter_fama.databinding.ActivityCharacterDetailsBinding
import com.example.harrypoter_fama.models.api.dto.CharacterResponse

class CharacterDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCharacterDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val characterResponse = intent.getParcelableExtra<CharacterResponse>("currentCharacter")
        val characterImage = intent.getByteArrayExtra("imagePersonaje")

        binding.vImage.setImageBitmap(characterImage?.toBitmap())
        binding.vName.text = characterResponse?.name
        binding.vHouse.text = characterResponse?.house
        binding.vActor.text = characterResponse?.actor?.trim()
        binding.vSpecieGender.text = "${characterResponse?.species} - ${characterResponse?.gender}"
        binding.vBornAge.text = "Nac: ${characterResponse?.dateOfBirth} (${characterResponse?.yearOfBirth1})"
        binding.vTextMaderaValue.text = characterResponse?.wand?.wood
        binding.vTextNucleoValue.text = characterResponse?.wand?.core
        binding.vTextLongitudValue.text = characterResponse?.wand?.length?.toString()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}