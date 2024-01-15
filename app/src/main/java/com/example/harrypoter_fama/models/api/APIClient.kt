package com.example.harrypoter_fama.models.api

import com.example.harrypoter_fama.models.api.dto.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface APIClient {

    @GET("characters")
    suspend fun getCharacters(
    ): Response<ArrayList<CharacterResponse>>
}