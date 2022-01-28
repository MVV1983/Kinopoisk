package com.example.kinopoisk.presenter

import com.example.kinopoisk.interfaces.Contract
import com.example.kinopoisk.model.datamodel.Film
import com.example.kinopoisk.model.repository.FilmRepository

class FilmPresenter(filmView: Contract.View) : Contract.Presenter {
    private var view: Contract.View = filmView
    private var model: Contract.Model = FilmRepository()

    override fun getDataFromApi() {
        model.getData(this)
    }

    override fun giveDataForUI(): MutableList<Film> {
     return model.getFilms()
    }

    override fun giveGenresDatafoUI(): List<String> {
        return model.getListAllGenres()
    }

    override fun updateUI() {
        view.updateViewData()
    }
}