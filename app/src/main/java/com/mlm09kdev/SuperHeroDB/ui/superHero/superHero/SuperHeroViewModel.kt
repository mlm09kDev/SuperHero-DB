package com.mlm09kdev.SuperHeroDB.ui.superHero.superHero

import androidx.lifecycle.ViewModel
import com.mlm09kdev.SuperHeroDB.model.repository.SuperHeroRepository
import com.mlm09kdev.SuperHeroDB.utils.lazyDeferred

class SuperHeroViewModel(private val superHeroRepository: SuperHeroRepository) : ViewModel() {

    val superHero by lazyDeferred {
        superHeroRepository.getSuperHero("76") }

}
