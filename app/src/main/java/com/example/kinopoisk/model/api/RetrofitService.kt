package com.example.kinopoisk.model.api

import com.example.kinopoisk.model.datamodel.Films
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    @GET("/sequeniatesttask/films.json")
    fun getFilms(): Call<Films>
}