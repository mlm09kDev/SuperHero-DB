package com.mlm09kdev.superHeroDB.ui.superhero.details

import androidx.lifecycle.ViewModel
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository
import com.mlm09kdev.superHeroDB.utils.lazyDeferred

class DetailsViewModel(private val superHeroRepository: SuperHeroRepository, id: Int) :
    ViewModel() {

    val superHero by lazyDeferred {
        superHeroRepository.getSuperHeroById(id)
    }

}
