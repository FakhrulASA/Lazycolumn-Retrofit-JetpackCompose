package com.fakhrulasa.jetpackcomposebasics.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofitlient {
    companion object{
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api:ApiInterface by lazy {
            retrofit.create(ApiInterface::class.java)
        }

    }
}