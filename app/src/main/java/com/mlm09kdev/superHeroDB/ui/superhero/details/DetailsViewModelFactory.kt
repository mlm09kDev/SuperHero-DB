package com.mlm09kdev.superHeroDB.ui.superhero.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository

/**
 * Created by Manuel Montes de Oca on 1/30/2020.
 */
class DetailsViewModelFactory(private val superHeroRepository: SuperHeroRepository,private val id: Int) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(superHeroRepository, id) as T
    }
}
