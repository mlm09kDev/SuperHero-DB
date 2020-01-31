package com.mlm09kdev.superHeroDB.ui.superhero.searchSuperHero

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository

/**
 * Created by Manuel Montes de Oca on 1/30/2020.
 */
class SearchViewModelFactory(private val superHeroRepository: SuperHeroRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(superHeroRepository) as T
    }
}