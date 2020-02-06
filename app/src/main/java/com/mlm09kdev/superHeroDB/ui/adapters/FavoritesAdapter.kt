package com.mlm09kdev.superHeroDB.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.utils.glide.GlideApp
import kotlinx.android.synthetic.main.item_superhero_list.view.*


class FavoritesAdapter(onSuperHeroClickListener: OnSuperHeroClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<SuperHeroEntity> = emptyList()
    private var onSuperHeroClickListener: OnSuperHeroClickListener

    init {
        this.onSuperHeroClickListener = onSuperHeroClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SuperHeroViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_superhero_list,
                parent,
                false
            ), onSuperHeroClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SuperHeroViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitSuperHeroList(list: List<SuperHeroEntity>) {
        items = list

    }

    internal inner class SuperHeroViewHolder constructor(
        ItemView: View,
        onSuperHeroClickListener: OnSuperHeroClickListener
    ) :
        RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        private var onSuperHeroClickListener: OnSuperHeroClickListener

        init {
            itemView.setOnClickListener(this)
            this.onSuperHeroClickListener = onSuperHeroClickListener
        }

        private val favoritesName = itemView.textView_favorites_name
        private val favoritesId = itemView.textView_favorites_id
        private val favoritesImage = itemView.imageView_favorites_image
        private val favoritesCity = itemView.textView_favorites_city

        fun bind(superHeroEntity: SuperHeroEntity) {
            favoritesName.text = superHeroEntity.name
            favoritesCity.text = superHeroEntity.biography.firstAppearance
            favoritesId.text = superHeroEntity.id

            GlideApp.with(itemView.context).load(superHeroEntity.image.url).centerCrop()
                .error(R.drawable.ic_broken_image)
                .into(favoritesImage)


        }

        override fun onClick(view: View?) {
            onSuperHeroClickListener.onItemClick(items[adapterPosition].id, view)
        }
    }

    interface OnSuperHeroClickListener {
        fun onItemClick(position: String, view: View?)
    }

}
