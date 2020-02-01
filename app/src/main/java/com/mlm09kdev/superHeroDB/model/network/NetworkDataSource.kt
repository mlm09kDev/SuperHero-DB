package com.mlm09kdev.superHeroDB.model.network

import androidx.lifecycle.LiveData
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.model.network.response.SearchResponse

/**
 * Created by Manuel Montes de Oca on 1/29/2020.
 */
interface NetworkDataSource {
    val downloadedSuperHero: LiveData<SearchResponse>
    suspend fun fetchSuperHero(id: String)
}