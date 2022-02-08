package com.example.kinopoisk.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.adapters.holders.FilmHolder
import com.example.kinopoisk.adapters.holders.GenresHolder
import com.example.kinopoisk.adapters.holders.HeaderHolder
import com.example.kinopoisk.model.datamodel.Film
import com.example.kinopoisk.model.datamodel.ListItem

class ItemFilmAdapter(private val context: Context, val clickListener: ItemClickInterface) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = mutableListOf<ListItem>()

    private companion object {
        const val HEADER = 1003
        const val GENRES = 1001
        const val FILM = 1002
    }

    fun update(data: List<ListItem>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
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
            is GenresHolder -> holder.bind(list[position], clickListener)
            is FilmHolder -> holder.bind(list[position], context, clickListener)
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

    interface ItemClickInterface {
        fun onClicked(film: Film)
        fun onClickGenres(genres: String)
    }
}