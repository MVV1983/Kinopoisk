package com.example.kinopoisk.presenter

import com.example.kinopoisk.interfaces.Contract
import com.example.kinopoisk.model.datamodel.Film
import com.example.kinopoisk.model.datamodel.Genres
import com.example.kinopoisk.model.datamodel.Header
import com.example.kinopoisk.model.datamodel.ListItem
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

    override fun giveGenresDataForUI(): List<Genres> {
        return model.getListAllGenres()
    }

    override fun updateUI() {
        view.updateViewData()
    }

    override fun getSelectedGenresFilm(genres: String):List<Film> {
       return model.getSelectedFilmByGenre(genres,this)
    }

    override fun mergeListForAdapter(listHeader: List<Header>,list1: List<Genres>, list2: List<Film>): List<ListItem> {
        return model.preparationData(listHeader,list1,list2)
    }


}