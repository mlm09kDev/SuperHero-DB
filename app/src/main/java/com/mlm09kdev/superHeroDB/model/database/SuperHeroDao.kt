package com.mlm09kdev.superHeroDB.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity

@Dao
interface SuperHeroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(superHeroEntity: SuperHeroEntity)

    @Query("select * from superHero order by id ASC")
    fun getAllSuperHero(): LiveData<List<SuperHeroEntity>>

    @Query("select * from superHero where name like :name order by id ASC")
    fun getSuperHero(name: String): List<SuperHeroEntity>

    @Query("select * from superHero where id = :id")
    fun getSuperHeroById(id: String): LiveData<SuperHeroEntity>

    @Query("select * from superHero where id = :id")
    fun getNoneLiveDataSuperHeroById(id: String): SuperHeroEntity

    @Query("select * from superHero where isFavorite = 1")
    fun getFavoriteSuperHero(): LiveData<List<SuperHeroEntity>>

    @Update
    fun update(superHeroEntity: SuperHeroEntity)

}