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
    private var listGenres: MutableList<Genres> = mutableListOf()
    private var allGenres: List<Genres> = mutableListOf()
    private var selectedFilm: List<Film> = mutableListOf()
    private var allFilmGenres: List<Genres> = mutableListOf()
    private var mergerListForAdapter: List<ListItem> = mutableListOf()
    private var header: List<Header> = listOf()
    private  var message: String = ""

    override fun getData(presenter: Contract.Presenter){
        val call = API.create().getFilms()
        call.enqueue(object : Callback<Films> {
            override fun onResponse(call: Call<Films>, response: Response<Films>) {
                val filmResponse = response.body()
                listFilm.clear()

                filmResponse?.let { listFilm.addAll(it.films) }
                listFilm.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.localized_name }))
                presenter.updateUI()
            }

            override fun onFailure(call: Call<Films>, t: Throwable) {
                Log.e("error", t.localizedMessage.toString())
                message = t.toString()
                presenter.updateUI()
            }
        })
    }

    override fun dataAdapter(presenter: Contract.Presenter): List<ListItem> {

        allFilmGenres = getListAllGenres2()
        header = listOf(Header("Жанры", "Фильмы"))

        mergerListForAdapter = if (selectedFilm.isEmpty()) {
            preparationData(header, allFilmGenres, listFilm)
        } else {
            preparationData(header, allFilmGenres, selectedFilm)
        }
        return mergerListForAdapter
    }

    override fun selectedDataGenres(string: String, presenter: Contract.Presenter) {
        selectedFilm = listFilm.filter { it.genres.contains(string) }
        presenter.updateUI()
    }

    override fun catchError(): String {
        return message
    }

    private fun getListAllGenres2(): List<Genres> {
        for (e in 0 until listFilm.size) {
            val unit = listFilm[e]
            for (i in unit.genres) {
                listGenres.addAll(listOf(Genres(i,false)))
            }
        }
        allGenres = listGenres.distinctBy { it.name }

        return allGenres
    }

    private fun preparationData(listHeader: List<Header>, list1: List<Genres>, list2: List<Film>): List<ListItem> {
        val header = listHeader.map { ListItem.HeaderModel(it.header) }
        val genres = list1.map { ListItem.GenresModel(it) }
        val footer = listHeader.map { ListItem.HeaderModel(it.footer) }
        val films = list2.map { ListItem.FilmModel(it) }

        return header + genres + footer + films
    }
}