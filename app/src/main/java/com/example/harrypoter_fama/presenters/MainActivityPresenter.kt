package com.example.harrypoter_fama.presenters

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.harrypoter_fama.Utils.Companion.toCharacterEntity
import com.example.harrypoter_fama.models.api.ApiAdapter
import com.example.harrypoter_fama.models.api.dto.CharacterResponse
import com.example.harrypoter_fama.models.database.AppDatabase
import com.example.harrypoter_fama.models.database.entities.Character
import com.example.harrypoter_fama.presenters.contracts.MainActivityContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class MainActivityPresenter(private val view: MainActivityContract.View) : MainActivityContract.Presenter, CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() { return Dispatchers.Main + job }

    override fun getAllCharactersFromApi(db: AppDatabase) {
        job= SupervisorJob()
        var apiResponse: Response<ArrayList<CharacterResponse>>? = null

        launch {
            withContext(Dispatchers.IO)
            {
                try {
                    apiResponse = ApiAdapter.apiClient.getCharacters()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main){
                        view.onGetCharacterError(e)
                    }
                }
            }

            if(apiResponse != null){
                if(apiResponse?.isSuccessful == true && apiResponse?.code() == 200 )
                    apiResponse?.body()?.let {
                        view.showCharacters(it)
                        saveAllCharactersInDb(db, it)
                    }
                else
                    apiResponse?.message()?.let { view.onResponseError(it) }
            }
        }
    }

    override suspend fun saveAllCharactersInDb(db:AppDatabase, characters: ArrayList<CharacterResponse>) {
            val dataToInsert = characters.toCharacterEntity()
            db.characterDao.inserAll(dataToInsert)
    }

    override suspend fun getAllCharactersFromDb(db:AppDatabase): List<Character> {
        return db.characterDao.getAll()
    }
}