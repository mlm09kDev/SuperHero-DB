package com.mlm09kdev.superHeroDB.ui.superhero.searchSuperHero

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository
import com.mlm09kdev.superHeroDB.utils.lazyDeferred
import kotlinx.coroutines.Deferred

class SearchViewModel(private val superHeroRepository: SuperHeroRepository) : ViewModel() {

    fun getSuperHeroListAsync(queryString: String): Deferred<LiveData<List<SuperHeroEntity>>> {
        Log.i("SearchView", "SearchViewModel")
        val superHero by lazyDeferred {
            superHeroRepository.getSuperHero(queryString)
        }
        return superHero
    }
    suspend fun updateFavorites(superHeroEntity: SuperHeroEntity){
        superHeroRepository.updateFavorite(superHeroEntity)
    }
}
