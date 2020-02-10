package com.mlm09kdev.superHeroDB.ui.superhero.superherolist

import androidx.lifecycle.ViewModel
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository
import com.mlm09kdev.superHeroDB.utils.lazyDeferred

class FavoritesViewModel(private val superHeroRepository: SuperHeroRepository) : ViewModel() {

    val superHeroList by lazyDeferred {
        superHeroRepository.getFavoriteSuperHeroes()
    }
}
