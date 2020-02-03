package com.mlm09kdev.superHeroDB.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity

@Dao
interface SuperHeroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAndInsert(superHeroEntity: SuperHeroEntity)

    @Query("select * from superHero")
    fun getAllSuperHero():LiveData<List<SuperHeroEntity>>

    //todo find way to change id to int instead of string
    @Query("select * from superHero where name like :name order by id ASC")
    fun getSuperHero(name: String):LiveData<List<SuperHeroEntity>>
}