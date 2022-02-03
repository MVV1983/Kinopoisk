package com.example.kinopoisk.model.repository

import android.util.Log
import com.example.kinopoisk.interfaces.Contract
import com.example.kinopoisk.model.api.API
import com.example.kinopoisk.model.datamodel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmRepository : Contract.Model {
    private var listFilm: MutableList<Film> = mutableListOf()
    var listGenres: MutableList<Genres> = mutableListOf()
    var allGenres: List<Genres> = mutableListOf()

    var selectedFilm: List<Film> = mutableListOf()

    override fun getData(presenter: Contract.Presenter) {
        val call = API.create().getFilms()
        call.enqueue(object : Callback<Films> {
            override fun onResponse(call: Call<Films>, response: Response<Films>) {
                val filmResponse = response.body()
                filmResponse.toString()
                listFilm.clear()
                filmResponse?.let { listFilm.addAll(it.films) }

                // println(listFilm)
                listFilm.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.localized_name }))

                presenter.updateUI()
            }

            override fun onFailure(call: Call<Films>, t: Throwable) {
                Log.e("error", t.localizedMessage)
            }

        })
    }

    override fun getFilms(): MutableList<Film> {
        return listFilm
    }

    override fun getListAllGenres(): List<Genres> {
        // pice code for  uses Genres data class

        for (e in 0 until listFilm.size) {
            val unit = listFilm[e]
            for (i in unit.genres) {
                listGenres.addAll(listOf(Genres(i)))
            }
        }
        allGenres = listGenres.distinctBy { it.name }

        return allGenres
    }

    override fun getSelectedFilmByGenre(genres: String, presenter: Contract.Presenter): List<Film> {
        val selectGenres = listFilm.filter { it.genres.contains(genres) }
        presenter.updateUI()
        return selectGenres
    }

    override fun preparationData(listHeader: List<Header>,list1: List<Genres>, list2: List<Film>): List<ListItem> {
        val header= listHeader.map { ListItem.HeaderModel(it.header) }
        val tmp2 = list1.map { ListItem.GenresModel(it) }
        val footer= listHeader.map { ListItem.HeaderModel(it.footer)}
        val tmp4 = list2.map { ListItem.FilmModel(it) }
        return header+tmp2+footer+tmp4
    }
}