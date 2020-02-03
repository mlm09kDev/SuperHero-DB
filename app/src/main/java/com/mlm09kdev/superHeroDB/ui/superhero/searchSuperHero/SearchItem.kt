package com.mlm09kdev.superHeroDB.ui.superhero.searchSuperHero

import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.utils.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_superhero_card.*

/**
 * Created by Manuel Montes de Oca on 2/2/2020.
 */
class SearchItem(val superHeroEntity: SuperHeroEntity) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_superHero_name.text = superHeroEntity.name
            textView_superHero_city.text = superHeroEntity.biography.publisher
            textView_superHero_id.text = "ID = "+superHeroEntity.id
            GlideApp.with(this.containerView).load(superHeroEntity.image.url).into(imageView_superHero_image)
        }
    }

    override fun getLayout() = R.layout.item_superhero_card
}