package com.example.harrypoter_fama.api

import com.example.harrypoter_fama.models.Character
import retrofit2.Response
import retrofit2.http.GET

interface APIClient {

    @GET("characters")
    suspend fun getCharacters(
    ): Response<ArrayList<Character>>
}