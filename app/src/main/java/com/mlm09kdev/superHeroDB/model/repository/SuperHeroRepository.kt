package com.mlm09kdev.superHeroDB.model.repository

import androidx.lifecycle.LiveData
import com.mlm09kdev.superHeroDB.model.network.response.SuperHeroResponse

/**
 * Created by Manuel Montes de Oca on 1/29/2020.
 */
interface SuperHeroRepository {
    suspend fun getSuperHero(id: String):LiveData<SuperHeroResponse>
}