package com.mlm09kdev.superHeroDB.model.repository

import androidx.lifecycle.LiveData
import com.mlm09kdev.superHeroDB.model.database.SuperHeroDao
import com.mlm09kdev.superHeroDB.model.network.NetworkDataSource
import com.mlm09kdev.superHeroDB.model.network.response.SuperHeroResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class SuperHeroRepositoryImpl(
    private val superHeroDao: SuperHeroDao,
    private val networkDataSource: NetworkDataSource
) : SuperHeroRepository {
    init {
        networkDataSource.downloadedSuperHero.observeForever { newSuperHero ->
            persistFetchedSuperHero(newSuperHero)
        }
    }

    override suspend fun getSuperHero(id: String): LiveData<SuperHeroResponse> {

        return withContext(Dispatchers.IO) {
            initSuperHeroData()
            return@withContext superHeroDao.getSuperHeroById(id) }
    }

    private fun persistFetchedSuperHero(featchedSuperHero: SuperHeroResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            superHeroDao.updateAndInsert(featchedSuperHero)

        }
    }

    private suspend fun initSuperHeroData() {
        if (isFetchingSuperHeroNeeded(ZonedDateTime.now().minusDays(2)))
            fetchSuperHero()
    }

    private suspend fun fetchSuperHero() {
        networkDataSource.fetchSuperHero("76")
    }

    //check API every 24 hours
    private fun isFetchingSuperHeroNeeded(lastFetchedTime: ZonedDateTime): Boolean {
        val timeSinceLastFeatch = ZonedDateTime.now().minusDays(1)
        return lastFetchedTime.isBefore(timeSinceLastFeatch)
    }
}