package com.mlm09kdev.superHeroDB.model.repository

import androidx.lifecycle.LiveData
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity

/**
 * Created by Manuel Montes de Oca on 1/29/2020.
 */
interface SuperHeroRepository {
    suspend fun getSuperHero(name: String):LiveData<List<SuperHeroEntity>>
}