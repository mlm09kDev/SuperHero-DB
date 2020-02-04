package com.mlm09kdev.superHeroDB.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity

/**
 * Created by Manuel Montes de Oca on 2/3/2020.
 */
@Dao
interface FavoriteDao {

  /*  @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAndInsert(superHeroEntity: SuperHeroEntity)

    @Query("delete from superHero where id = :id")
    fun deleteSuperHerobyId(id: String)*/
}