package com.example.kinopoisk.presenter

import com.example.kinopoisk.interfaces.Contract
import com.example.kinopoisk.model.datamodel.ListItem
import com.example.kinopoisk.model.repository.FilmRepository

class FilmPresenter(filmView: Contract.View) : Contract.Presenter {
    private var view: Contract.View = filmView
    private var model: Contract.Model = FilmRepository()

    private var data: List<ListItem> = mutableListOf()
    ////MVP
    override fun getDataFromApi() {
        model.getData(this)
    }

    override fun updateUI() {
        view.updateViewData()
    }

    override fun getDataForAdapter() {
        data =  model.dataAdapter(this)
        view.updateAdapter(data)
    }

    override fun sendSelected(genres: String) {
        model.selectedDataGenres(genres,this)
    }


}