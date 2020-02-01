package com.mlm09kdev.superHeroDB.model.repository

import androidx.lifecycle.LiveData
import com.mlm09kdev.superHeroDB.model.database.SuperHeroDao
import com.mlm09kdev.superHeroDB.model.network.NetworkDataSource
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
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
            for (item in newSuperHero.results) {
                persistFetchedSuperHero(item)
            }
        }
    }

    override suspend fun getSuperHero(name: String): LiveData<List<SuperHeroEntity>> {

        return withContext(Dispatchers.IO) {
            initSuperHeroData(name)
            return@withContext superHeroDao.getSuperHero(name)
        }
    }

    private fun persistFetchedSuperHero(fetchedSuperHero: SuperHeroEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            superHeroDao.updateAndInsert(fetchedSuperHero)

        }
    }

    private suspend fun initSuperHeroData(name: String) {
        if (isFetchingSuperHeroNeeded(ZonedDateTime.now().minusDays(2)))
            fetchSuperHero(name)
    }

    private suspend fun fetchSuperHero(name: String) {
        networkDataSource.fetchSuperHero(name)
    }

    //check API every 24 hours
    //TODO: check only once every 24
    private fun isFetchingSuperHeroNeeded(lastFetchedTime: ZonedDateTime): Boolean {
        // val timeSinceLastFetch = ZonedDateTime.now().minusDays(1)
        // return lastFetchedTime.isBefore(timeSinceLastFetch)
        return true
    }
}