package com.mlm09kdev.superHeroDB.model.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mlm09kdev.superHeroDB.model.network.response.SearchResponse
import com.mlm09kdev.superHeroDB.utils.NoConnectionException

class NetworkDataSourceImpl(private val superHeroAPIService: SuperHeroAPIService) :
    NetworkDataSource {

    private val _downloadedSuperHero = MutableLiveData<SearchResponse>()
    override val downloadedSuperHero: LiveData<SearchResponse>
        get() = _downloadedSuperHero

    override suspend fun fetchSuperHero(name: String) {
        try {
            val fetchedSuperHero = superHeroAPIService.getSuperHeroList(name)
            _downloadedSuperHero.postValue(fetchedSuperHero)
        }
        //catch custom Exception and log it
        catch (e:NoConnectionException){
            Log.e("Connectivity", "No Connectivity", e)
        }
    }
}