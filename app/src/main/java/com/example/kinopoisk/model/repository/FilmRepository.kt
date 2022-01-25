package com.example.kinopoisk.model.repository

import com.example.kinopoisk.interfaces.FilmInterface

class FilmRepository: FilmInterface.FilmModel {

    override fun getFilms() {

    }

    override fun getFilmNameByGenres(films: String, presenter: FilmInterface.FilmPresenter) {
        TODO("Not yet implemented")
    }

    override fun getGenres(genres: String, presenter: FilmInterface.FilmPresenter) {
        TODO("Not yet implemented")
    }
}