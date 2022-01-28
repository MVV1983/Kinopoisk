package com.example.kinopoisk.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kinopoisk.R
import com.squareup.picasso.Picasso


class FilmInfoFragment : Fragment() {
    lateinit var name: TextView
    lateinit var description: TextView
    lateinit var year_film: TextView
    lateinit var cover: ImageView
    lateinit var raiting: TextView

    companion object {
        private const val NAME = "name"
        private const val LOCAL_NAME = "local_name"
        private const val FILM_COVER = "film_cover"
        private const val FILM_DESCRIPTION = "film_description"
        private const val FILM_YEAR = "year"
        private const val RATING = "rating"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_film_info, container, false)

        (requireActivity() as MainActivity).supportActionBar?.title = arguments?.getString(LOCAL_NAME)

        name = view?.findViewById(R.id.film_name)!!
        name.text = arguments?.getString(NAME)

        description = view.findViewById(R.id.film_description)!!
        cover = view.findViewById(R.id.film_cover)
        year_film = view.findViewById(R.id.film_year)
        raiting = view.findViewById(R.id.film_rating)
        description.text = arguments?.getString(FILM_DESCRIPTION)
        cover.contentDescription = arguments?.getString(LOCAL_NAME)

        val imageCoverUri = arguments?.getString(FILM_COVER)
        Picasso.with(context).load(imageCoverUri).into(cover)
        var yFilm = arguments?.getString(FILM_YEAR)

        year_film.text = getString(R.string.film_year, yFilm)
        raiting.text = getString(R.string.film_rating, arguments?.getString(RATING))

        return view
    }

}