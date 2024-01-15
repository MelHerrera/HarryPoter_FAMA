package com.example.harrypoter_fama.presenters.contracts

import com.example.harrypoter_fama.models.api.dto.CharacterResponse
import com.example.harrypoter_fama.models.database.AppDatabase
import com.example.harrypoter_fama.models.database.entities.Character

public  interface MainActivityContract{

    interface View{
        suspend fun showCharacters(characterResponses:List<CharacterResponse>)
        fun onGetCharacterError(error:Exception)

        fun onResponseError(message:String)
    }

    interface Presenter{

        fun getAllCharactersFromApi(db:AppDatabase)
        suspend fun saveAllCharactersInDb(db:AppDatabase,characters: ArrayList<CharacterResponse>)
        suspend fun getAllCharactersFromDb(db:AppDatabase):List<Character>
    }
}