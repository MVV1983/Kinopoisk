package com.example.kinopoisk.presenter

import com.example.kinopoisk.interfaces.FilmInterface
import com.example.kinopoisk.model.repository.FilmRepository

class FilmPresenter(filmView: FilmInterface.FilmView) : FilmInterface.FilmPresenter {
    private var view: FilmInterface.FilmView = filmView
    private var model: FilmInterface.FilmModel = FilmRepository()


    override fun callingNetwork(film: String) {
        model.getFilms()
    }

    override fun showFilm(): String {
        return "fff"
    }

    override fun UIUpdate() {
        view.updateViewData()
    }
}