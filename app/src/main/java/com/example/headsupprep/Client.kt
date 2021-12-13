package com.example.headsupprep

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
    private var varRetrofit : Retrofit? = null

    fun requestClient(): Retrofit? {
        varRetrofit = Retrofit.Builder().baseUrl("https://dojo-recipes.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

            return varRetrofit
    }
}