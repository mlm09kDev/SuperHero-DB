package com.mlm09kdev.superHeroDB.ui.superhero.search

import com.google.android.material.snackbar.Snackbar
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.utils.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.search_item_superhero_card.*
import kotlinx.android.synthetic.main.search_item_superhero_card.view.*


/**
 * Created by Manuel Montes de Oca on 2/2/2020.
 */
class SearchItem(val superHeroEntity: SuperHeroEntity, onClickListener: OnItemClickListener) : Item() {

    private var onItemClickListener: OnItemClickListener = onClickListener

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_search_name.text = superHeroEntity.name
            textView_search_publisher.text = superHeroEntity.biography.publisher
            textView_search_first_appeared.text = superHeroEntity.biography.firstAppearance
            textView_search_alignment.text = superHeroEntity.biography.alignment.capitalize()
            checkbox_search_favoriteCheck.isChecked = superHeroEntity.isFavorite == 1
            GlideApp.with(this.containerView).load(superHeroEntity.image.url).centerCrop()
                .error(R.drawable.ic_broken_image)
                .into(imageView_search_image)
        }
        viewHolder.itemView.checkbox_search_favoriteCheck.setOnClickListener {

            if (viewHolder.itemView.checkbox_search_favoriteCheck.isChecked) {
                Snackbar.make(it, "Added to Favorites", Snackbar.LENGTH_SHORT).show()
                superHeroEntity.isFavorite = 1
            } else {
                Snackbar.make(it, "Removed from Favorites", Snackbar.LENGTH_SHORT).show()
                superHeroEntity.isFavorite = 0
            }
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(superHeroEntity)

        }
    }

    override fun getLayout() = R.layout.search_item_superhero_card

    interface OnItemClickListener {
        fun onItemClick(superHeroEntity: SuperHeroEntity)
    }

}