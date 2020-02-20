package com.mlm09kdev.superHeroDB.ui.superhero.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val superHeroRepository: SuperHeroRepository) : ViewModel() {

    val searchList = MutableLiveData<List<SuperHeroEntity>>()

    fun fetchSuperHeroList(queryString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchList.postValue(superHeroRepository.getSuperHero(queryString))
        }
    }

    fun updateFavorites(superHeroEntity: SuperHeroEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            superHeroRepository.updateFavorite(superHeroEntity)
        }
    }
}
