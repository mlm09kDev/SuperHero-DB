package com.mlm09kdev.superHeroDB.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity

@Dao
interface SuperHeroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAndInsert(superHeroEntity: SuperHeroEntity)

    @Query("select * from superHero order by id ASC")
    fun getAllSuperHero():LiveData<List<SuperHeroEntity>>

    //todo find way to change id to int instead of string
    @Query("select * from superHero where name like :name order by id ASC")
    fun getSuperHero(name: String):LiveData<List<SuperHeroEntity>>


    @Query("select * from superHero where id = :id")
    fun getSuperHeroById(id: String):LiveData<SuperHeroEntity>

    @Query("delete from superHero where id = :id")
    fun deleteSuperHeroById(id: String)

}