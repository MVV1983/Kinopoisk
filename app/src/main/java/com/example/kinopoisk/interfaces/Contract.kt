package com.example.kinopoisk.interfaces

import com.example.kinopoisk.model.datamodel.*

interface Contract {
    interface Model {
        fun getData(presenter: Presenter)
        fun dataAdapter(presenter: Presenter) : List<ListItem>
        fun selectedDataGenres(string: String,presenter: Presenter)

        fun catchError():String
    }

    interface View {
        fun updateViewData()
        fun updateAdapter(lAdapter: List<ListItem>)

        fun showMessage(string: String)
    }

    interface Presenter {
        fun getDataFromApi()
        fun updateUI()
        fun getDataForAdapter()
        fun sendSelected(genres: String)

        fun getError()
    }
}