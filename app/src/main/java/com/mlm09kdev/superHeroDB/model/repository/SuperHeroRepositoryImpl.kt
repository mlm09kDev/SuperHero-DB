package com.mlm09kdev.superHeroDB.model.repository

import androidx.lifecycle.LiveData
import com.mlm09kdev.superHeroDB.model.database.SuperHeroDao
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.model.network.NetworkDataSource
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
        networkDataSource.apply {
            downloadedSuperHero.observeForever { newSuperHero ->
                if (newSuperHero.results != null) {
                    for (item in newSuperHero.results) {
                        persistFetchedSuperHero(item)
                    }
                }
            }
        }
    }

    override suspend fun getSuperHero(name: String): List<SuperHeroEntity> {
        return withContext(Dispatchers.IO) {
            initSuperHeroData(name)
            return@withContext superHeroDao.getSuperHero(name)
        }
    }

    override suspend fun getSuperHeroById(id: Int): LiveData<SuperHeroEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext superHeroDao.getSuperHeroById(id)
        }
    }

    override suspend fun getFavoriteSuperHeroes(): LiveData<List<SuperHeroEntity>> {
        return withContext(Dispatchers.IO) {
            return@withContext superHeroDao.getFavoriteSuperHero()
        }
    }

    override suspend fun updateFavorite(superHeroEntity:SuperHeroEntity) {
        superHeroDao.update(superHeroEntity)
    }
    override suspend fun createSuperHero(superHeroEntity:SuperHeroEntity) {
        insertOrUpdate(superHeroEntity)
    }

    private fun persistFetchedSuperHero(fetchedSuperHero: SuperHeroEntity) {
        GlobalScope.launch(Dispatchers.IO) {
           insertOrUpdate(fetchedSuperHero)

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
        val timeSinceLastFetch = ZonedDateTime.now().minusDays(1)
        return lastFetchedTime.isBefore(timeSinceLastFetch)
    }

    private fun insertOrUpdate(newSuperHeroEntity: SuperHeroEntity) {
        var oldSuperHeroEntity = superHeroDao.getNoneLiveDataSuperHeroById(newSuperHeroEntity.id)
        if (oldSuperHeroEntity == null)
            superHeroDao.insert(newSuperHeroEntity)
        else
            superHeroDao.update(oldSuperHeroEntity)
    }
}