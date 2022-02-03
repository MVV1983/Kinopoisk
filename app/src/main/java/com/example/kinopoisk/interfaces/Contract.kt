package com.example.kinopoisk.interfaces

import com.example.kinopoisk.model.datamodel.*

interface Contract {
    interface Model {
        fun getData(presenter: Presenter)
       // fun getFilms(): MutableList<Film>
      //  fun getListAllGenres(): List<Genres>
        //fun getSelectedFilmByGenre(genres: String, presenter: Presenter): List<Film>

       // fun preparationData(listHeader: List<Header>, list1: List<Genres>, list2: List<Film>): List<ListItem>

        //MVP
        fun dataAdapter(presenter: Presenter) : List<ListItem>
        fun selectedDataGenres(string: String,presenter: Presenter)
    }

    interface View {
        fun updateViewData()
        fun updateAdapter(lAdapter: List<ListItem>)
    }

    interface Presenter {
        fun getDataFromApi()
       // fun giveDataForUI(): MutableList<Film>
       // fun giveGenresDataForUI(): List<Genres>
        fun updateUI()
       // fun getSelectedGenresFilm(genres: String): List<Film>

       // fun mergeListForAdapter(listHeader: List<Header>, list1: List<Genres>, list2: List<Film>): List<ListItem>

        //test for adapter loaddata MVP
        fun getDataForAdapter()
        fun sendSelected(genres: String)
    }
}