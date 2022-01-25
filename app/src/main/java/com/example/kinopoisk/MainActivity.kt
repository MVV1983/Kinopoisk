package com.example.kinopoisk

import com.example.kinopoisk.model.api.API
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kinopoisk.model.datamodel.Film
import com.example.kinopoisk.model.datamodel.Films
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var listUsers: MutableList<Films> =
            mutableListOf<Films>()
    private var adapter: UsersAdapter? = null

    private var listFilm: MutableList<Film> =
        mutableListOf<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listUsers = mutableListOf()


        recycler_view.layoutManager = GridLayoutManager(this@MainActivity,2)// 2 span for recyclerview

        //adapter = UsersAdapter(this, listUsers)
        // recycler_view.adapter = adapter

        getJson()
    }


    private fun getJson() {

        val call = API.create().getFilms()
        call.enqueue(object : Callback<Films> {
            override fun onResponse(call: Call<Films>, response: Response<Films>) {
                val filmResponse = response.body()
                filmResponse.toString()
                listFilm.clear()
                //filmResponse?.let { listUsers.addAll(listOf(it)) }
                filmResponse?.let { listFilm.addAll(it.films) }

                listFilm.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.localized_name }))
                adapter = UsersAdapter(baseContext, listFilm)
                recycler_view.adapter = adapter
                adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Films>, t: Throwable) {
                Log.e("error", t.localizedMessage)
            }

        })
    }

}