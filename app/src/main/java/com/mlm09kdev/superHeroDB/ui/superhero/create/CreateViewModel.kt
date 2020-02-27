package com.mlm09kdev.superHeroDB.ui.superhero.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlm09kdev.superHeroDB.model.database.entity.*
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateViewModel(private val superHeroRepository: SuperHeroRepository) : ViewModel() {

    fun createNewSuperHero(superHeroEntity: SuperHeroEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            superHeroRepository.createSuperHero(superHeroEntity)
        }
    }

}
