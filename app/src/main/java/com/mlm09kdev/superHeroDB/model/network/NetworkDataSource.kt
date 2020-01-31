package com.mlm09kdev.superHeroDB.model.network

import androidx.lifecycle.LiveData
import com.mlm09kdev.superHeroDB.model.network.response.SuperHeroResponse

/**
 * Created by Manuel Montes de Oca on 1/29/2020.
 */
interface NetworkDataSource {
    val downloadedSuperHero: LiveData<SuperHeroResponse>
    suspend fun fetchSuperHero(id: String)
}