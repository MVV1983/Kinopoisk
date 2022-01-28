package com.example.kinopoisk.model.repository

import android.util.Log
import com.example.kinopoisk.interfaces.Contract
import com.example.kinopoisk.model.api.API
import com.example.kinopoisk.model.datamodel.Film
import com.example.kinopoisk.model.datamodel.Films
import com.example.kinopoisk.model.datamodel.Genres
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmRepository : Contract.Model {
    private var listFilm: MutableList<Film> = mutableListOf()
    var listGenres: MutableList<String> = mutableListOf()
    var allGenres: List<String> = mutableListOf()

    override fun getData(presenter: Contract.Presenter) {
        val call = API.create().getFilms()
        call.enqueue(object : Callback<Films> {
            override fun onResponse(call: Call<Films>, response: Response<Films>) {
                val filmResponse = response.body()
                filmResponse.toString()
                listFilm.clear()
                filmResponse?.let { listFilm.addAll(it.films) }

                listFilm.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.localized_name }))

                for (e in 0 until listFilm.size){
                    val unit = listFilm[e]
                    for(i in  unit.genres){
                       listGenres.addAll(unit.genres)// listGenres.add(listFilm[e].genres.toString())
                    }
                }
                // pice code for  uses Genres data class
                var listing: MutableList<Genres> = mutableListOf()
                for (e in 0 until listFilm.size){
                    val unit = listFilm[e]
                    for(i in  unit.genres){
                        listing.addAll(listOf(Genres(unit.genres)))// listGenres.add(listFilm[e].genres.toString())
                    }
                }

                allGenres = listGenres.distinctBy { it.length }
                println(allGenres.toString())// println(listGenres.toString())

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

    override fun getListAllGenres(): List<String> {
        return allGenres//listGenres
    }

}