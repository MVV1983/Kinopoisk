package com.example.kinopoisk.model.datamodel

import com.google.gson.annotations.SerializedName

data class Films(
        @SerializedName("films")
        val films: MutableList<Film>
)