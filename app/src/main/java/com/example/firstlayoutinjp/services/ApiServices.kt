package com.example.firstlayoutinjp.services

import com.example.firstlayoutinjp.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiServices {
    @GET("movielist.json")
    suspend fun getMovies() : List<Movie>

    companion object {
        var apiService: ApiServices? = null
        fun getInstance() : ApiServices {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://howtodoandroid.com/apis/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiServices::class.java)
            }
            return apiService!!
        }
    }
}