package com.example.kinopoisk.adapters.holders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.R
import com.example.kinopoisk.adapters.ItemFilmAdapter
import com.example.kinopoisk.model.datamodel.ListItem
import kotlinx.android.synthetic.main.item_genres.view.*

class GenresHolder(parent: ViewGroup, context: Context) : RecyclerView.ViewHolder(
    LayoutInflater.from(context).inflate(R.layout.item_genres, parent, false)
) {
    var name: Button = itemView.name_genres_button

    fun bind(item: ListItem, clickListener: ItemFilmAdapter.ItemClickInterface) {
        val pos = item as ListItem.GenresModel
        name.text = pos.genres.name
        val itemSelect = pos.genres.name

        itemView.name_genres_button.setOnClickListener {
            clickListener.onClickGenres(pos.genres.name)
            item.genres.isSelected = !item.genres.isSelected
        }

        if (pos.genres.isSelected && item.genres.name == itemSelect) {
            name.setBackgroundResource(R.drawable.button_press)
            item.genres.isSelected = false
        } else {
            name.setBackgroundResource(R.drawable.button_not_pressed)
        }
    }
}