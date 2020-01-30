package com.mlm09kdev.SuperHeroDB.model.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mlm09kdev.SuperHeroDB.model.network.response.SuperHeroResponse
import com.mlm09kdev.SuperHeroDB.utils.NoConnectionException

class NetworkDataSourceImpl(private val superHeroAPIService: SuperHeroAPIService) :
    NetworkDataSource {

    private val _downloadedSuperHero = MutableLiveData<SuperHeroResponse>();
    override val downloadedSuperHero: LiveData<SuperHeroResponse>
        get() = _downloadedSuperHero

    override suspend fun fetchSuperHero(id: String) {
        try {
            val fetchedSuperHero = superHeroAPIService.getSuperHero(id)
            _downloadedSuperHero.postValue(fetchedSuperHero)
        }
        //catch custom Exception and log it
        catch (e:NoConnectionException){
            Log.e("Connectivity", "No Connection", e)
        }
    }
}