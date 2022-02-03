package com.example.kinopoisk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.model.datamodel.Film
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UsersAdapter(private val context: Context, private var list: List<Film>,
                   val clickLisstener: ItemClickInterface
) :
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.card_view, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val film = list.get(position)
        holder.name?.text = film.localized_name

        Picasso.with(context).load(film.image_url).into(holder.image, object : Callback {
            override fun onSuccess() {
                Picasso.with(context).load(film.image_url).into(holder.image)
            }

            override fun onError() {
                holder.error?.visibility = View.VISIBLE
            }
        })

        holder.itemView.setOnClickListener {
            val unit = list.get(position)
            clickLisstener.onClicked(unit)

            notifyDataSetChanged()
        }

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var name: TextView? = null
        var info1: TextView? = null
        var info2: TextView? = null
        var genres: TextView? = null
        var image: ImageView? = null
        var error: TextView? = null

        init {
            name = view.findViewById(R.id.localized_name)
            info1 = view.findViewById(R.id.film_info1)
            info2 = view.findViewById(R.id.film_info2)
            genres = view.findViewById(R.id.film_genres)
            image = view.findViewById(R.id.card_Image)
            error = view.findViewById(R.id.error_text_message)
        }
    }


    interface ItemClickInterface {
        fun onClicked(film: Film)
    }

}