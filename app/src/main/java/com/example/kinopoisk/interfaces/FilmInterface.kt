package com.example.kinopoisk.interfaces

interface FilmInterface {
    interface FilmModel{
        fun getFilms()
        fun getFilmNameByGenres(films: String,presenter: FilmPresenter)
        fun getGenres(genres: String,presenter: FilmPresenter)
    }
    interface FilmView{
        fun updateViewData()
    }
    interface  FilmPresenter{
        fun callingNetwork(film: String)
        fun showFilm():String
        fun UIUpdate()
    }
}