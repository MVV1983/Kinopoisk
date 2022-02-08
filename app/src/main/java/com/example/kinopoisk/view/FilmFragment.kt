package com.example.kinopoisk.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.kinopoisk.R
import com.example.kinopoisk.interfaces.Contract
import com.example.kinopoisk.adapters.ItemFilmAdapter
import com.example.kinopoisk.model.datamodel.Film
import com.example.kinopoisk.model.datamodel.ListItem
import com.example.kinopoisk.presenter.FilmPresenter
import kotlinx.android.synthetic.main.fragment_main.*

class FilmFragment : Fragment(), ItemFilmAdapter.ItemClickInterface,
    Contract.View {
    private lateinit var presenter: Contract.Presenter
    private lateinit var adapter: ItemFilmAdapter

    companion object {
        const val HEADER = 1003
        const val GENRES = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecycler()
        presenter = FilmPresenter(this)
        presenter.getDataFromApi()

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

    override fun onClickGenres(genres: String) {
        presenter.sendSelected(genres)
        Toast.makeText(context, genres, Toast.LENGTH_LONG).show()
    }

    override fun updateViewData() {
        presenter.getDataForAdapter()
        presenter.getError()
    }

    override fun updateAdapter(lAdapter: List<ListItem>) {
        adapter.update(lAdapter)
        adapter.notifyDataSetChanged()
    }

    override fun showMessage(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }

    private fun prepareRecycler() {
        val glm = GridLayoutManager(context, 2)
        glm.setSpanSizeLookup(object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    HEADER -> 2
                    GENRES -> 2
                    else -> 1
                }
            }
        })
        recycler_view.layoutManager = glm
        adapter = context?.let { ItemFilmAdapter(it, this) }!!
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
    }
}