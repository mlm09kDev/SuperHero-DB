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
            textView_search_name.text = superHeroEntity.name
            textView_search_publisher.text = superHeroEntity.biography.publisher
            textView_search_first_appeared.text = superHeroEntity.biography.firstAppearance
            textView_search_alignment.text = superHeroEntity.biography.alignment.capitalize()
            GlideApp.with(this.containerView).load(superHeroEntity.image.url).centerCrop().error(R.drawable.ic_broken_image)
                .into(imageView_search_image)
        }
    }

    override fun getLayout() = R.layout.item_superhero_card

}