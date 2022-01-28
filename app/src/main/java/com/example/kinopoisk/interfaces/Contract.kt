package com.example.kinopoisk.interfaces

import com.example.kinopoisk.model.datamodel.Film

interface Contract {
    interface Model {
        fun getData(presenter: Presenter)
        fun getFilms():MutableList<Film>
        fun getListAllGenres():List<String>
    }

    interface View {
        fun updateViewData()
    }

    interface Presenter {
        fun getDataFromApi()
        fun giveDataForUI():MutableList<Film>
        fun giveGenresDatafoUI():List<String>
        fun updateUI()
    }
}