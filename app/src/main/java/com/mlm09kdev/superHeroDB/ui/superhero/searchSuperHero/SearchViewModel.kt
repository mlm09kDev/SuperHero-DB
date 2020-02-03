package com.mlm09kdev.superHeroDB.ui.superhero.searchSuperHero

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository
import com.mlm09kdev.superHeroDB.utils.lazyDeferred
import kotlinx.coroutines.Deferred

class SearchViewModel(private val superHeroRepository: SuperHeroRepository) : ViewModel() {

    fun getSuperHeroList(queryString: String):Deferred<LiveData<List<SuperHeroEntity>>> {

        val superHero by lazyDeferred {
            superHeroRepository.getSuperHero(queryString)
        }
        return superHero
    }
}
