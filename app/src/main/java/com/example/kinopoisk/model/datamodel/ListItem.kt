package com.example.kinopoisk.model.datamodel


sealed class ListItem {
    class HeaderModel(val name: String): ListItem()
    class GenresModel(val genres: Genres) : ListItem()
    class FilmModel(val film: Film) : ListItem()
}