package com.example.kinopoisk.interfaces

import android.R.attr.button
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.R
import com.example.kinopoisk.model.datamodel.Film
import com.example.kinopoisk.model.datamodel.ListItem
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_genres.view.*


class ItemFilmAdapter(private val context: Context, val clickListener: ItemClickInterface) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = mutableListOf<ListItem>()

    private companion object {
        const val HEADER = 1003
        const val GENRES = 1001
        const val FILM = 1002
    }



    //update all elements list
    fun update(data: List<ListItem>) {
        if (data.isNotEmpty()) {
            list.clear()
            list.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> HeaderHolder(parent, context)
            GENRES -> GenresHolder(parent, context)
            FILM -> FilmHolder(parent, context)
            else -> throw IllegalArgumentException("Invalid type of data ")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder) {
            is HeaderHolder -> holder.bind(list[position])
            is GenresHolder -> holder.bind(list[position])
            is FilmHolder -> holder.bind(list[position])
            else -> throw IllegalArgumentException("Invalid type of data ")
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is ListItem.HeaderModel -> HEADER
            is ListItem.GenresModel -> GENRES
            is ListItem.FilmModel -> FILM
        }
    }
    inner class HeaderHolder(parent: ViewGroup, context: Context) : RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.row_header, parent, false)
    ) {

        var name: TextView? = null

        init {
            name = itemView.findViewById(R.id.name_header)

        }

        fun bind(item: ListItem) {
            val pos = item as ListItem.HeaderModel
            name?.text = pos.name

        }

    }

    inner class GenresHolder(parent: ViewGroup, context: Context) : RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_genres, parent, false)
    ) {

        var name: Button? = null

        init {
            name = itemView.findViewById(R.id.name_genres_button)
        }

        fun bind(item: ListItem) {
            val pos = item as ListItem.GenresModel
            name?.text = pos.genres.name


            itemView.name_genres_button.setOnClickListener {
                clickListener.onClickGenres(pos.genres.name)
                notifyDataSetChanged()
            }
        }
    }

    inner class FilmHolder(parent: ViewGroup, context: Context) : RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.card_view, parent, false)
    ) {

        var name: TextView? = null
        var info1: TextView? = null
        var info2: TextView? = null
        var genres: TextView? = null
        var image: ImageView? = null
        var error: TextView? = null

        init {
            name = itemView.findViewById(R.id.localized_name)
            info1 = itemView.findViewById(R.id.film_info1)
            info2 = itemView.findViewById(R.id.film_info2)
            genres = itemView.findViewById(R.id.film_genres)
            image = itemView.findViewById(R.id.card_Image)
            error = itemView.findViewById(R.id.error_text_message)
        }

        fun bind(item: ListItem) {
            val pos = item as ListItem.FilmModel
            name?.text = pos.film.localized_name

            Picasso.with(context).load(pos.film.image_url).into(image, object : Callback {
                override fun onSuccess() {
                    Picasso.with(context).load(pos.film.image_url).into(image)
                }

                override fun onError() {
                    error?.visibility = View.VISIBLE
                }
            })
            itemView.setOnClickListener {
                clickListener.onClicked(pos.film)
                notifyDataSetChanged()
            }
        }

    }

    interface ItemClickInterface {
        fun onClicked(film: Film)
        fun onClickGenres(genres: String)
    }
}