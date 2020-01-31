package com.mlm09kdev.SuperHeroDB.ui.superHero.superHero

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mlm09kdev.SuperHeroDB.model.repository.SuperHeroRepository

/**
 * Created by Manuel Montes de Oca on 1/30/2020.
 */
class SuperHeroViewModelFactory(private val superHeroRepository: SuperHeroRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SuperHeroViewModel(superHeroRepository) as T
    }
}