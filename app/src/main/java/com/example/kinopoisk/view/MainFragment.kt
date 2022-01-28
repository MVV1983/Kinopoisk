package com.example.kinopoisk.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kinopoisk.R
import com.example.kinopoisk.UsersAdapter
import com.example.kinopoisk.interfaces.Contract
import com.example.kinopoisk.model.api.API
import com.example.kinopoisk.model.datamodel.Film
import com.example.kinopoisk.model.datamodel.Films
import com.example.kinopoisk.model.datamodel.Genres
import com.example.kinopoisk.presenter.FilmPresenter
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment(), UsersAdapter.ItemClickInterface, Contract.View {
    private var presenter: Contract.Presenter? = null
    private var adapter: UsersAdapter? = null

    private lateinit var listFilm: MutableList<Film>
    private lateinit var allFilmGenres: List<String>

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

        presenter = FilmPresenter(this)
        presenter?.getDataFromApi()
        presenter?.giveGenresDatafoUI()
    }

    override fun onClicked(film: Film) {

        val args = Bundle()
        args.putString("name", film.name)
        args.putString("local_name", film.localized_name)
        args.putString("film_cover", film.image_url)
        args.putString("year", film.year.toString())
        args.putString("film_description", film.description)
        args.putString("rating", film.rating.toString())

        findNavController().navigate(R.id.action_mainFragment_to_filmInfoFragment, args)
    }

    override fun updateViewData() {
        allFilmGenres = presenter?.giveGenresDatafoUI()!!

        listFilm = presenter?.giveDataForUI()!!
        adapter = context?.let { UsersAdapter(it, listFilm, this@MainFragment) }
        recycler_view.adapter = adapter
        adapter?.notifyDataSetChanged()
    }
}