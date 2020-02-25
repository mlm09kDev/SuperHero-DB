package com.mlm09kdev.superHeroDB.model.repository

import androidx.lifecycle.LiveData
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity

/**
 * Created by Manuel Montes de Oca on 1/29/2020.
 */
interface SuperHeroRepository {
    suspend fun getSuperHero(name: String): List<SuperHeroEntity>
    suspend fun getSuperHeroById(id: Int): LiveData<SuperHeroEntity>
    suspend fun getFavoriteSuperHeroes(): LiveData<List<SuperHeroEntity>>
    suspend fun updateFavorite(superHeroEntity: SuperHeroEntity)
    suspend fun createSuperHero(superHeroEntity:SuperHeroEntity)
}