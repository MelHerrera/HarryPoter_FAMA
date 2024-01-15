package com.example.harrypoter_fama.models.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiAdapter {
    private const val baseUrl = "https://hp-api.onrender.com/api/"

    private val client: OkHttpClient = OkHttpClient.Builder().build()

    val apiClient: APIClient = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIClient::class.java)
}