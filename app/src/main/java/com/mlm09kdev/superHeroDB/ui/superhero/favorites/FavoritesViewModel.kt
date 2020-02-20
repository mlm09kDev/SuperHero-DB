package com.mlm09kdev.superHeroDB.ui.superhero.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository
import com.mlm09kdev.superHeroDB.utils.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(private val superHeroRepository: SuperHeroRepository) : ViewModel() {

    val superHeroList by lazyDeferred {
        superHeroRepository.getFavoriteSuperHeroes()
    }

    fun updateFavorites(superHeroEntity: SuperHeroEntity, favorite: Int) {
        superHeroEntity.isFavorite = favorite
        viewModelScope.launch(Dispatchers.IO) {
            superHeroRepository.updateFavorite(superHeroEntity)
        }
    }
}
