package com.mlm09kdev.SuperHeroDB.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mlm09kdev.SuperHeroDB.model.response.SuperHeroResponse

@Database(
    entities = [SuperHeroResponse::class], version = 1
)
abstract class SuperHeroDatabase: RoomDatabase() {
    abstract fun superHeroDao(): SuperHeroDao

    companion object{
        @Volatile
        private var instace: SuperHeroDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context:Context) =instace ?: synchronized(LOCK){
            instace ?: buildDatabase(context).also { instace = it}
        }

        private fun buildDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext,
                SuperHeroDatabase::class.java,"superHero.db")
                .build()

    }
}