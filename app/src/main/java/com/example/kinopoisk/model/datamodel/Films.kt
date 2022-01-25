package com.example.kinopoisk.model.datamodel

import com.example.kinopoisk.model.datamodel.Film
import com.google.gson.annotations.SerializedName

data class Films(
        @SerializedName("films")
        val films: MutableList<Film>
)