package com.fakhrulasa.jetpackcomposebasics.network

import retrofit2.http.GET

interface ApiInterface {
    @GET("posts")
    suspend fun getPost():List<PostData>
}