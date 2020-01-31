package com.mlm09kdev.superHeroDB.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mlm09kdev.superHeroDB.model.network.response.SuperHeroResponse

@Dao
interface SuperHeroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAndInsert(superHeroResponse: SuperHeroResponse)

    @Query("select * from superHero")
    fun getAllSuperHero():LiveData<List<SuperHeroResponse>>

    //todo find way to change id to int instead of string
    @Query("select * from superHero where id = :id")
    fun getSuperHeroById(id: String):LiveData<SuperHeroResponse>

    @Query("select * from superHero where name = :name")
    fun getSuperHeroByName(name: String):LiveData<SuperHeroResponse>
}