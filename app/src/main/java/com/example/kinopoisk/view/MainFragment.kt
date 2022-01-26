package com.example.kinopoisk.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kinopoisk.R
import com.example.kinopoisk.UsersAdapter
import com.example.kinopoisk.model.api.API
import com.example.kinopoisk.model.datamodel.Film
import com.example.kinopoisk.model.datamodel.Films
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment(), UsersAdapter.ItemClickInterface {
    private var listUsers: MutableList<Films> =
        mutableListOf<Films>()
    private var adapter: UsersAdapter? = null

    private var listFilm: MutableList<Film> =
        mutableListOf<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.layoutManager = GridLayoutManager(context, 2)// 2 span for recyclerview

        //adapter = UsersAdapter(this, listUsers)
        // recycler_view.adapter = adapter

        val call = API.create().getFilms()
        call.enqueue(object : Callback<Films> {
            override fun onResponse(call: Call<Films>, response: Response<Films>) {
                val filmResponse = response.body()
                filmResponse.toString()
                listFilm.clear()
                //filmResponse?.let { listUsers.addAll(listOf(it)) }
                filmResponse?.let { listFilm.addAll(it.films) }

                listFilm.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.localized_name }))


                adapter = context?.let { UsersAdapter(it, listFilm, this@MainFragment) }
                recycler_view.adapter = adapter
                adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Films>, t: Throwable) {
                Log.e("error", t.localizedMessage)
            }

        })
    }


    override fun onClicked(film: Film) {

        val args = Bundle()

        args.putString("NAME", film.name)
        args.putString("LOCAL_NAME", film.localized_name)
        args.putString("FILM_COVER", film.image_url)
        args.putString("FILM_DESCRIPTION", film.description)
        args.putString("YEAR", film.year.toString())
        args.putString("FILM_DESCRIPTION", film.description)
        args.putString("RAITING", film.rating.toString())

        findNavController().navigate(R.id.action_mainFragment_to_filmInfoFragment, args)
    }
}
