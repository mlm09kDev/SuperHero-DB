package com.mlm09kdev.superHeroDB.ui.superhero.searchSuperHero

import androidx.lifecycle.ViewModel
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository
import com.mlm09kdev.superHeroDB.utils.lazyDeferred

class SearchViewModel(private val superHeroRepository: SuperHeroRepository) : ViewModel() {

    val superHero by lazyDeferred {
        superHeroRepository.getSuperHero("Batman") }

}
