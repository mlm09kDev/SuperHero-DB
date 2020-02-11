package com.mlm09kdev.superHeroDB.ui.superhero.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository

class FavoritesViewModelFactory(private val superHeroRepository: SuperHeroRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoritesViewModel(superHeroRepository) as T
    }
}